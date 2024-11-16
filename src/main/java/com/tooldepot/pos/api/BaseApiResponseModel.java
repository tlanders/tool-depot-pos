package com.tooldepot.pos.api;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public abstract class BaseApiResponseModel {
    protected int resultCode;
    protected String message;
}
