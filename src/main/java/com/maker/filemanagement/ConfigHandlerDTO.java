package com.maker.filemanagement;

import com.maker.filemanagement.out.IFileManagement;
import com.maker.filemanagement.out.ISerializer;
import com.maker.foldermaker.config.models.CfgDTOParent;

public record ConfigHandlerDTO(ISerializer<CfgDTOParent> serializer, IFileManagement fileManagement){}
