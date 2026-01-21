package se.deved.twitter_clone.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import se.deved.twitter_clone.models.Post;
import se.deved.twitter_clone.models.PostReaction;
import se.deved.twitter_clone.models.User;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface IPostReactionRepository extends JpaRepository<PostReaction, UUID> {
    Optional<PostReaction> findByUserAndPost(User user, Post post);
}
