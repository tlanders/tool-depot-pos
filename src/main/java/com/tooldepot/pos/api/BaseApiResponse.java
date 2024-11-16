package com.tooldepot.pos.api;

import lombok.Data;

import java.io.Serializable;

@Data
public abstract class BaseApiResponse implements Serializable {
    protected int resultCode;
    protected String message;
}
