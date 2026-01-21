package se.deved.twitter_clone.dtos;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class ReactPostRequest {
    private boolean liked;
    private String username;
    private String password;
    private UUID postId;
}
