package com.jemmic.addressbook.model;

import java.util.HashMap;
import java.util.Map;

/**
 * Enum to hold information about Family Description
 * @author Ritesh Kumar (ritesh.kumar.j18@gmail.com)
 *
 */
public enum FamilyDescription {

    PARENT(1, "Parent"),
    SON(2,"Son"),
    DAUGHTER(3,"Daughter"),
    HUSBAND(4,"Husband"),
    WIFE(5,"Wife"),
    GRANDPARENT(6,"Grand Parent"),
    SISTER(7,"Sister"),
    BROTHER(8,"Brother"),
    UNCLE(9,"Uncle"),
    AUNT(10,"Aunt"),
    COUSIN(11,"Cousin");

    private static final Map<Integer, FamilyDescription> BY_SRNO = new HashMap<>();
    private static final Map<String, FamilyDescription> BY_RELATION = new HashMap<>();

    static {
        for (FamilyDescription e : values()) {
            BY_SRNO.put(e.srNo, e);
            BY_RELATION.put(e.relation, e);
        }
    }

    public final int srNo;
    public final String relation;

    FamilyDescription(int srNo, String relation) {
        this.srNo = srNo;
        this.relation = relation;
    }

    public static String getAllRelation(){
        String allRelationText = "";
        for (FamilyDescription e : values()) {
            allRelationText= allRelationText + e.srNo +". "+ e.relation +"\n";
        }
        return allRelationText;
    }

    public static String getRelationText(int srNo){
        return BY_SRNO.get(srNo).relation;
    }

    public static FamilyDescription getFamilyDesc(int srNo){
        return BY_SRNO.get(srNo);
    }
    public static FamilyDescription getFamilyDescByName(String name){
        return BY_RELATION.get(name);
    }
}
