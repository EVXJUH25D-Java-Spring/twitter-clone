package se.deved.twitter_clone.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import se.deved.twitter_clone.models.Post;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface IPostRepository extends JpaRepository<Post, UUID> {
    @Query("SELECT p FROM posts p LEFT JOIN FETCH p.comments WHERE p.id = :id")
    Optional<Post> findByIdWithComments(@Param("id") UUID id);

    // För custom metoder + paging:
    // Page<Post> findByTitle(String title, Pageable pageable);
}