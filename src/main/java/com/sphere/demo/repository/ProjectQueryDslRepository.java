package com.sphere.demo.repository;

import com.querydsl.core.BooleanBuilder;
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

@Repository
public class ProjectQueryDslRepository {

    private final JPAQueryFactory query;

    public ProjectQueryDslRepository(EntityManager em) {
        this.query = new JPAQueryFactory(em);
    }

    public Page<Project> findAll(ProjectSearchCond projectSearchCond, Pageable pageable) {
        BooleanBuilder whereClause = getWhereClause(projectSearchCond);

        List<Project> projectList = query.selectFrom(project)
//                .leftJoin(project.projectPositionSet, projectPosition)
//                .leftJoin(projectPosition.position, position)
//                .leftJoin(project.projectTechnologySet, projectTechnology)
                .where(whereClause)
                .orderBy(project.createdAt.desc())
                .offset(pageable.getOffset()).limit(pageable.getPageSize())
                .fetch();

        Long count = query.select(project.countDistinct())
                .from(project)
//                .leftJoin(project.projectPositionSet, projectPosition)
//                .leftJoin(projectPosition.position, position)
//                .leftJoin(project.projectTechnologySet, projectTechnology)
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

    private BooleanBuilder getWhereClause(ProjectSearchCond projectSearchCond) {
        if (projectSearchCond == null) {
            return null;
        }

        BooleanBuilder builder = new BooleanBuilder();

        String title = projectSearchCond.getTitle();
        if (!StringUtils.isNullOrEmpty(title)) {
            builder.and(project.title.containsIgnoreCase(title));
        }

        Boolean isRecruiting = projectSearchCond.getIsRecruiting();
        if (isRecruiting != null && isRecruiting) {
            builder.and(project.status.eq(ProjectState.RECRUITING));
        }

        String positionName = projectSearchCond.getPositionName();
        if (!StringUtils.isNullOrEmpty(positionName)) {
            builder.and(project.projectPositionSet.any().position.name.eq(positionName));
        }

        String techName = projectSearchCond.getTechName();
        if (!StringUtils.isNullOrEmpty(techName)) {
            builder.and(project.projectTechnologySet.any().name.eq(techName));
        }

        return builder;
    }
}
