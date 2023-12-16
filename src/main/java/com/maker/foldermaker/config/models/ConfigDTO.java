package com.maker.foldermaker.config.models;

public record ConfigDTO(int rangeGroups, String mainFolderName, String mainFolderPath, String mainUri, boolean useNumberOfLab)
    implements CfgDTOParent
{}

//
// {"rangeGroups":6,"mainFolderName":"Lab_*_Entregas_(Pendientes_De_Revision)","mainFolderPath":"C:/Users/yoloo/Desktop/Ayudantias/Lab_Ciencias","mainUri":"https://campusvirtual.ufro.cl/mod/assign/view.php?id\u003d1610508\u0026action\u003dgrading","useNumberOfLab":true}
