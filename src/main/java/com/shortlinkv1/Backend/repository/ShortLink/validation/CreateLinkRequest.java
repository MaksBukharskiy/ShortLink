package com.shortlinkv1.Backend.repository.ShortLink.validation;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

public record CreateLinkRequest (

    @NotBlank(message = "Original URL must not be blank")
    @Pattern(
            regexp = "^(https?://).+",
            message = "URL must start with http:// or https://"
    )
    String originalUrl,

    @Size(max = 10, message = "No more than 10 tags allowed")
    List<String> tags

){}
