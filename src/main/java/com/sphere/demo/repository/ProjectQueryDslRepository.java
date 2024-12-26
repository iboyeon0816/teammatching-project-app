package com.sphere.demo.repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.util.StringUtils;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sphere.demo.domain.Project;
import com.sphere.demo.domain.enums.ProjectState;
import com.sphere.demo.web.dto.project.ProjectRequestDto.ProjectSearchCond;
import jakarta.persistence.EntityManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.sphere.demo.domain.QPlatform.platform;
import static com.sphere.demo.domain.QPosition.position;
import static com.sphere.demo.domain.QProject.project;
import static com.sphere.demo.domain.QUser.user;
import static com.sphere.demo.domain.QUserRefreshToken.userRefreshToken;
import static com.sphere.demo.domain.mapping.QProjectApplication.projectApplication;
import static com.sphere.demo.domain.mapping.QProjectPlatform.projectPlatform;
import static com.sphere.demo.domain.mapping.QProjectPosition.projectPosition;

// TODO: tech 추가 필요
@Repository
public class ProjectQueryDslRepository {

    private static final Integer DEFAULT_PAGE_SIZE = 4;
    private static final Integer FIRST_PAGE = 0;
    private final JPAQueryFactory query;

    public ProjectQueryDslRepository(EntityManager em) {
        this.query = new JPAQueryFactory(em);
    }

    public Page<Project> findAll(ProjectSearchCond projectSearchCond, Pageable pageable) {

        BooleanBuilder whereClause = getWhereClause(projectSearchCond);

        List<Project> projectList = query.selectFrom(project)
                .leftJoin(project.user, user).fetchJoin()
                .leftJoin(user.userRefreshToken, userRefreshToken).fetchJoin()
                .leftJoin(project.projectPlatformSet, projectPlatform).fetchJoin()
                .leftJoin(projectPlatform.platform, platform).fetchJoin()
                .leftJoin(project.projectPositionSet, projectPosition).fetchJoin()
                .leftJoin(projectPosition.position, position).fetchJoin()
                .where(whereClause)
                .orderBy(project.createdAt.desc())
                .offset(pageable.getOffset()).limit(pageable.getPageSize())
                .fetch();

        Long count = query.select(project.countDistinct())
                .from(project)
                .leftJoin(project.projectPositionSet, projectPosition)
                .leftJoin(projectPosition.position, position)
                .leftJoin(project.projectPlatformSet, projectPlatform)
                .leftJoin(projectPlatform.platform, platform)
                .where(whereClause)
                .fetchFirst();

        return new PageImpl<>(projectList, pageable, count);
    }

    public Optional<Project> findDetailById(Long projectId) {

        Project found = query.selectFrom(project)
                .leftJoin(project.user, user).fetchJoin()
                .leftJoin(user.userRefreshToken, userRefreshToken).fetchJoin()
                .leftJoin(project.projectPositionSet, projectPosition).fetchJoin()
                .leftJoin(projectPosition.position, position).fetchJoin()
                .leftJoin(project.projectPlatformSet, projectPlatform).fetchJoin()
                .leftJoin(projectPlatform.platform, platform).fetchJoin()
                .leftJoin(projectPosition.projectApplicationList, projectApplication).fetchJoin()
                .where(project.id.eq(projectId))
                .fetchOne();

        return Optional.ofNullable(found);
    }

    public List<Project> findNewProjects(Boolean mostViews) {

        return query.selectFrom(project)
                .leftJoin(project.user, user).fetchJoin()
                .leftJoin(user.userRefreshToken, userRefreshToken).fetchJoin()
                .leftJoin(project.projectPositionSet, projectPosition).fetchJoin()
                .leftJoin(projectPosition.position, position).fetchJoin()
                .leftJoin(project.projectPlatformSet, projectPlatform).fetchJoin()
                .leftJoin(projectPlatform.platform, platform).fetchJoin()
                .where(project.status.eq(ProjectState.RECRUITING))
                .orderBy(getOrderSpecifiers(mostViews))
                .offset(FIRST_PAGE).limit(DEFAULT_PAGE_SIZE)
                .fetch();
    }

    private BooleanBuilder getWhereClause(ProjectSearchCond projectSearchCond) {

        if (projectSearchCond == null) {
            return null;
        }

        String title = projectSearchCond.getTitle();
        ProjectState projectState = projectSearchCond.getProjectState();
        String platformName = projectSearchCond.getPlatformName();
        String positionName = projectSearchCond.getPositionName();
        String techStackName = projectSearchCond.getTechStackName();

        BooleanBuilder builder = new BooleanBuilder();

        if (!StringUtils.isNullOrEmpty(title)) {
            builder.and(project.title.contains(title));
        }

        if (projectState != null) {
            builder.and(project.status.eq(projectState));
        }

        if (!StringUtils.isNullOrEmpty(platformName)) {
            builder.and(project.projectPlatformSet.any().platform.name.eq(platformName));
        }

        if (!StringUtils.isNullOrEmpty(positionName)) {
            builder.and(project.projectPositionSet.any().position.name.eq(positionName));
        }

        return builder;
    }

    private OrderSpecifier[] getOrderSpecifiers(Boolean mostViews) {
        List<OrderSpecifier> orderSpecifierList = new ArrayList<>();

        if (mostViews) {
            orderSpecifierList.add(project.view.desc());
        }
        orderSpecifierList.add(project.createdAt.desc());
        return orderSpecifierList.toArray(OrderSpecifier[]::new);
    }
}
