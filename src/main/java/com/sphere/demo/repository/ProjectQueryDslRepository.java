package com.sphere.demo.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sphere.demo.domain.*;
import com.sphere.demo.domain.enums.ProjectState;
import com.sphere.demo.domain.mapping.QProjectPlatform;
import com.sphere.demo.domain.mapping.QProjectRecruitPosition;
import com.sphere.demo.domain.mapping.QProjectTechStack;
import jakarta.persistence.EntityManager;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ProjectQueryDslRepository {

    private final EntityManager em;
    private final JPAQueryFactory query;

    public ProjectQueryDslRepository(EntityManager em) {
        this.em = em;
        this.query = new JPAQueryFactory(em);
    }

    public List<Project> findWithMostViews() {
        QProject project = QProject.project;
        QUser user = QUser.user;
        QProjectRecruitPosition projectPosition = QProjectRecruitPosition.projectRecruitPosition;
        QPosition position = QPosition.position;
        QProjectTechStack projectTechStack = QProjectTechStack.projectTechStack;
        QTechnologyStack techStack = QTechnologyStack.technologyStack;
        QUserRefreshToken userRefreshToken = QUserRefreshToken.userRefreshToken;
        QProjectPlatform projectPlatform = QProjectPlatform.projectPlatform;
        QPlatform platform = QPlatform.platform;

        return query.selectFrom(project)
                .leftJoin(project.user, user).fetchJoin()
                .leftJoin(user.userRefreshToken, userRefreshToken).fetchJoin()
                .leftJoin(project.projectRecruitPositionSet, projectPosition).fetchJoin()
                .leftJoin(projectPosition.position, position).fetchJoin()
                .leftJoin(project.projectTechStackSet, projectTechStack).fetchJoin()
                .leftJoin(projectTechStack.technologyStack, techStack).fetchJoin()
                .leftJoin(project.projectPlatformSet, projectPlatform).fetchJoin()
                .leftJoin(projectPlatform.platform, platform).fetchJoin()
                .where(project.status.eq(ProjectState.RECRUITMENT))
                .orderBy(project.view.desc(), project.createdAt.desc())
                .offset(0).limit(4)
                .fetch();
    }
}
