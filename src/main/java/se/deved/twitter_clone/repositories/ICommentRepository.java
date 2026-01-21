package se.deved.twitter_clone.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import se.deved.twitter_clone.models.Comment;

import java.util.UUID;

@Repository
public interface ICommentRepository extends JpaRepository<Comment, UUID> {}
