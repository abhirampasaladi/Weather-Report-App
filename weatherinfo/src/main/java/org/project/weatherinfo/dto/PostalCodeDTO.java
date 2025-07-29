package org.project.weatherinfo.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
public class PostalCodeDTO {

    @NotBlank(message = "Postal Code Cannot be Null!")
    @Pattern(regexp = "^\\d{5}(-\\d{4})?$", message = "Postal code must be in the 5-4 / 5 digits in format 12345 or 12345-6789!")
    private String postalCode;
}
