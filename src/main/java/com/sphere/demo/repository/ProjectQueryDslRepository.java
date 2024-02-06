package com.sphere.demo.repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.util.StringUtils;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sphere.demo.domain.Project;
import com.sphere.demo.domain.QProject;
import com.sphere.demo.domain.enums.ProjectState;
import com.sphere.demo.web.dto.ProjectRequestDto.ProjectSearchCond;
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
import static com.sphere.demo.domain.QTechnologyStack.technologyStack;
import static com.sphere.demo.domain.QUser.user;
import static com.sphere.demo.domain.QUserRefreshToken.userRefreshToken;
import static com.sphere.demo.domain.mapping.QProjectMatch.projectMatch;
import static com.sphere.demo.domain.mapping.QProjectPlatform.projectPlatform;
import static com.sphere.demo.domain.mapping.QProjectRecruitPosition.projectRecruitPosition;
import static com.sphere.demo.domain.mapping.QProjectTechStack.projectTechStack;

@Repository
public class ProjectQueryDslRepository {

    private static final Integer DEFAULT_SIZE = 4;
    private static final Integer FIRST_PAGE = 0;

    private final EntityManager em;
    private final JPAQueryFactory query;

    public ProjectQueryDslRepository(EntityManager em) {
        this.em = em;
        this.query = new JPAQueryFactory(em);
    }

    public List<Project> findNewProjects(Boolean mostViews) {

        return query.selectFrom(project)
                .leftJoin(project.user, user).fetchJoin()
                .leftJoin(user.userRefreshToken, userRefreshToken).fetchJoin()
                .leftJoin(project.projectRecruitPositionSet, projectRecruitPosition).fetchJoin()
                .leftJoin(projectRecruitPosition.position, position).fetchJoin()
                .leftJoin(project.projectTechStackSet, projectTechStack).fetchJoin()
                .leftJoin(projectTechStack.technologyStack, technologyStack).fetchJoin()
                .leftJoin(project.projectPlatformSet, projectPlatform).fetchJoin()
                .leftJoin(projectPlatform.platform, platform).fetchJoin()
                .where(project.status.eq(ProjectState.RECRUITMENT))
                .orderBy(getOrderSpecifiers(mostViews))
                .offset(FIRST_PAGE).limit(DEFAULT_SIZE)
                .fetch();
    }

    public Optional<Project> findById(Long projectId) {

        Project found = query.selectFrom(project)
                .leftJoin(project.user, user).fetchJoin()
                .leftJoin(user.userRefreshToken, userRefreshToken).fetchJoin()
                .leftJoin(QProject.project.projectRecruitPositionSet, projectRecruitPosition).fetchJoin()
                .leftJoin(QProject.project.projectTechStackSet, projectTechStack).fetchJoin()
                .leftJoin(QProject.project.projectPlatformSet, projectPlatform).fetchJoin()
                .leftJoin(projectRecruitPosition.projectMatchList, projectMatch).fetchJoin()
                .where(QProject.project.id.eq(projectId))
                .fetchOne();

        return Optional.ofNullable(found);
    }

    public Page<Project> findAll(ProjectSearchCond projectSearchCond, Pageable pageable) {

        BooleanBuilder whereClause = getWhereClause(projectSearchCond);

        List<Project> projectList = query.selectFrom(project)
                .leftJoin(project.user, user).fetchJoin()
                .leftJoin(user.userRefreshToken, userRefreshToken).fetchJoin()
                .leftJoin(project.projectPlatformSet, projectPlatform).fetchJoin()
                .leftJoin(projectPlatform.platform, platform).fetchJoin()
                .leftJoin(project.projectRecruitPositionSet, projectRecruitPosition).fetchJoin()
                .leftJoin(projectRecruitPosition.position, position).fetchJoin()
                .leftJoin(project.projectTechStackSet, projectTechStack).fetchJoin()
                .leftJoin(projectTechStack.technologyStack, technologyStack).fetchJoin()
                .where(whereClause)
                .orderBy(project.createdAt.desc())
                .offset(pageable.getOffset()).limit(pageable.getPageSize())
                .fetch();

        Long count = query.select(project.countDistinct())
                .from(project)
                .leftJoin(project.projectRecruitPositionSet, projectRecruitPosition)
                .leftJoin(projectRecruitPosition.position, position)
                .leftJoin(project.projectTechStackSet, projectTechStack)
                .leftJoin(projectTechStack.technologyStack, technologyStack)
                .leftJoin(project.projectPlatformSet, projectPlatform)
                .leftJoin(projectPlatform.platform, platform)
                .where(whereClause)
                .fetchFirst();

        return new PageImpl<>(projectList, pageable, count);
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
            builder.and(project.projectRecruitPositionSet.any().position.name.eq(positionName));
        }

        if (!StringUtils.isNullOrEmpty(techStackName)) {
            builder.and(project.projectTechStackSet.any().technologyStack.name.eq(techStackName));
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
