package com.maker.foldermaker.config.models;

public record UserDTO(String username, String password)
    implements CfgDTOParent
{}
