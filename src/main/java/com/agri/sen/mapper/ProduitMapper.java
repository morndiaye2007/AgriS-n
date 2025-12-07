//package com.agri.sen.mapper;
//
//import com.agri.sen.entity.ProduitEntity;
//import com.agri.sen.model.ProduitDTO;
//import org.mapstruct.Mapper;
//import org.mapstruct.Mapping;
//import org.mapstruct.Named;
//import org.mapstruct.ReportingPolicy;
//
//import java.text.SimpleDateFormat;
//import java.util.Objects;
//import java.util.stream.Collectors;
//
//@Mapper(
//        componentModel = "spring",
//        unmappedTargetPolicy = ReportingPolicy.IGNORE
//)
//public interface ProduitMapper extends EntityMapper<ProduitDTO, ProduitEntity> {
//
//    @Mapping(target = "pays", source = "paysId", qualifiedByName = "getPays")
//    ProduitEntity asEntity(ProduitDTO dto);
//
//
//
//    @Mapping(target = "pays", source = "pays.libelle")
//    @Mapping(target = "langue", source = "langue.libelle")
//    @Mapping(target = "poste", source = "poste.intitule")
//    @Mapping(target = "filiereSuivi", source = "filiereSuivi.libelle")
//    @Mapping(target = "dateNaissance", source = "dateNaissance", qualifiedByName = "formatDate")
//    @Mapping(target = "sexe", source = "sexe", qualifiedByName = "formatSexe")
//    @Mapping(target = "typeContrat", source = "typeContrat", qualifiedByName = "formatTypeContrat")
//    @Mapping(target = "statutProduit", source = "statutProduit", qualifiedByName = "formatStatutProduit")
//    @Mapping(target = "preferences", source = "preferences", qualifiedByName = "formatPreferences")
//
//    @Named("formatDate")
//    default String formatDate(java.util.Date date) {
//        if (date == null) return "";
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//        return sdf.format(date);
//    }
//
//}