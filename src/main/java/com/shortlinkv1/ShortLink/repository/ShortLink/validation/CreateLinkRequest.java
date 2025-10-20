package com.shortlinkv1.ShortLink.repository.ShortLink.validation;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import org.springframework.web.bind.annotation.PathVariable;

public record CreateLinkRequest (

    @NotBlank(message = "Original URL must not be blank")
    @Pattern(regexp = "^(https?://).+", message = "URL must start with http:// or https://")
    String originalUrl

){}
