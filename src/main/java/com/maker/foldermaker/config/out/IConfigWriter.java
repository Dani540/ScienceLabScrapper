package com.maker.foldermaker.config.out;

public interface IConfigWriter<DTO> {
    void write(DTO configDTO);
}
