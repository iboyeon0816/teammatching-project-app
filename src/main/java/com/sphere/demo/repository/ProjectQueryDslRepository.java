package com.sphere.demo.repository;

import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sphere.demo.domain.Project;
import com.sphere.demo.domain.enums.ProjectState;
import jakarta.persistence.EntityManager;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

import static com.sphere.demo.domain.QPlatform.platform;
import static com.sphere.demo.domain.QPosition.position;
import static com.sphere.demo.domain.QProject.project;
import static com.sphere.demo.domain.QTechnologyStack.technologyStack;
import static com.sphere.demo.domain.QUser.user;
import static com.sphere.demo.domain.QUserRefreshToken.userRefreshToken;
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

    public List<Project> findNewProject(Boolean mostViews) {

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

    private OrderSpecifier[] getOrderSpecifiers(Boolean mostViews) {
        List<OrderSpecifier> orderSpecifierList = new ArrayList<>();

        if (mostViews) {
            orderSpecifierList.add(project.view.desc());
        }
        orderSpecifierList.add(project.createdAt.desc());
        return orderSpecifierList.toArray(OrderSpecifier[]::new);
    }
}
