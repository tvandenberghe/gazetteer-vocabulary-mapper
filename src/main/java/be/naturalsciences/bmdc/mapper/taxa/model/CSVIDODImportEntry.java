/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package be.naturalsciences.bmdc.mapper.taxa.model;

import com.opencsv.bean.CsvBindByName;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import org.apache.commons.lang3.ObjectUtils;

/**
 *
 * @author thomas
 *
 * A class to describe one row of the csv format that is used to import
 * taxonomic information into IDOD
 */
public class CSVIDODImportEntry {

    public enum HEADER_NAMES {
        ORIGINAL_CLADE_SCHOOL {
            public String toString() {
                return "original_clade_school";
            }
        },
        TAXON_NAME {
            public String toString() {
                return "name";
            }
        },//taxon_name
        PARENT_NAME {
            public String toString() {
                return "txn_name";
            }
        },//parent_name
        PARENT_SEQNO {
            public String toString() {
                return "txn_seqno";
            }
        },//parent_seqno
        ORIGINATORS_CODE {
            public String toString() {
                return "originators_code";
            }
        },//orig code
        LEVEL {
            public String toString() {
                return "tll_name";
            }
        },//level
        LEVEL_SEQNO {
            public String toString() {
                return "tll_seqno";
            }
        },//
        CLADE_SCHOOL_CODE {
            public String toString() {
                return "csl_code";
            }
        },//clade_school
        CLADE_SCHOOL_SEQNO {
            public String toString() {
                return "csl_seqno";
            }
        },
        CLADE_TYPE {
            public String toString() {
                return "clade_type";
            }
        },//clade_type
        OFF_TAXON {
            public String toString() {
                return "txn_name_off";
            }
        },//off_taxon
        OFF_TAXON_SEQNO {
            public String toString() {
                return "txn_seqno_off";
            }
        },
        TXN_ORDER {
            public String toString() {
                return "txn_order";
            }
        },//txn_order
        OFF_PUBLICATION {
            public String toString() {
                return "off_publication";
            }
        },//off_publication
        TRIVIAL_NAME {
            public String toString() {
                return "trivial_name";
            }
        },//trivial_name
        PARENT_OFF_TAXON {
            public String toString() {
                return "parent_off_name";
            }
        },
        ORIGINAL_NAME {
            public String toString() {
                return "original_name";
            }
        },
        MAPPED {
            public String toString() {
                return "mapped";
            }
        },
        TESTED_NAME {
            public String toString() {
                return "tested_name";
            }
        },
        REWRITTEN_NAME {
            public String toString() {
                return "rewritten_name";
            }
        };
    }

    public static final List<String> HEADER = new ArrayList(Arrays.asList(
            HEADER_NAMES.ORIGINAL_NAME.toString(),
            HEADER_NAMES.ORIGINAL_CLADE_SCHOOL.toString(),
            HEADER_NAMES.REWRITTEN_NAME.toString(),
            HEADER_NAMES.MAPPED.toString(),
            HEADER_NAMES.TESTED_NAME.toString(),
            HEADER_NAMES.TAXON_NAME.toString(),// "name", //taxon_name
            HEADER_NAMES.PARENT_NAME.toString(),// "txn_name", //parent_name
            HEADER_NAMES.PARENT_SEQNO.toString(),// "txn_seqno", //parent_seqno
            HEADER_NAMES.ORIGINATORS_CODE.toString(),// "originators_code", //orig code
            HEADER_NAMES.LEVEL.toString(),// "TLL_NAME", //level
            HEADER_NAMES.LEVEL_SEQNO.toString(),// "TLL_SEQNO",
            HEADER_NAMES.CLADE_SCHOOL_CODE.toString(),// "CSL_CODE", //clade_school
            HEADER_NAMES.CLADE_SCHOOL_SEQNO.toString(),// "CSL_SEQNO",
            HEADER_NAMES.CLADE_TYPE.toString(),// "clade_type", //clade_type
            HEADER_NAMES.OFF_TAXON.toString(),// "TXN_NAME_OFF", //off_taxon
            HEADER_NAMES.OFF_TAXON_SEQNO.toString(),// "TXN_SEQNO_OFF",
            HEADER_NAMES.TXN_ORDER.toString(),// "txn_order", //txn_order
            HEADER_NAMES.OFF_PUBLICATION.toString(),// "off_publication", //off_publication
            HEADER_NAMES.TRIVIAL_NAME.toString(),// "trivial_name", //trivial_name
            HEADER_NAMES.PARENT_OFF_TAXON.toString()// "PARENT_OFF_NAME", //parent_off_taxon
    ));

    /*public static final Map<String, Integer> HEADER_MAP = new LinkedHashMap();

    static {
        HEADER_MAP.put(HEADER_NAMES.ORIGINAL_NAME.toString(), 0);
        HEADER_MAP.put(HEADER_NAMES.ORIGINAL_CLADE_SCHOOL.toString(), 1);
        HEADER_MAP.put(HEADER_NAMES.REWRITTEN_NAME.toString(), 2);
        HEADER_MAP.put(HEADER_NAMES.MAPPED.toString(), 3);
        HEADER_MAP.put(HEADER_NAMES.TESTED_NAME.toString(), 4);
        HEADER_MAP.put(HEADER_NAMES.TAXON_NAME.toString(), 5);
        HEADER_MAP.put(HEADER_NAMES.PARENT_NAME.toString(), 6);
        HEADER_MAP.put(HEADER_NAMES.PARENT_SEQNO.toString(), 7);
        HEADER_MAP.put(HEADER_NAMES.ORIGINATORS_CODE.toString(), 8);
        HEADER_MAP.put(HEADER_NAMES.LEVEL.toString(), 9);
        HEADER_MAP.put(HEADER_NAMES.LEVEL_SEQNO.toString(), 10);
        HEADER_MAP.put(HEADER_NAMES.CLADE_SCHOOL_CODE.toString(), 11);
        HEADER_MAP.put(HEADER_NAMES.CLADE_SCHOOL_SEQNO.toString(), 12);
        HEADER_MAP.put(HEADER_NAMES.CLADE_TYPE.toString(), 13);
        HEADER_MAP.put(HEADER_NAMES.OFF_TAXON.toString(), 14);
        HEADER_MAP.put(HEADER_NAMES.OFF_TAXON_SEQNO.toString(), 15);
        HEADER_MAP.put(HEADER_NAMES.TXN_ORDER.toString(), 16);
        HEADER_MAP.put(HEADER_NAMES.OFF_PUBLICATION.toString(), 17);
        HEADER_MAP.put(HEADER_NAMES.TRIVIAL_NAME.toString(), 18);
        HEADER_MAP.put(HEADER_NAMES.PARENT_OFF_TAXON.toString(), 19);
    }*/
    @CsvBindByName(column = "original_identifier", required = true)
    private String originalIdentifier;
    @CsvBindByName(column = "original_name", required = true)
    private String originalName;
    @CsvBindByName(column = "original_clade_school", required = true)
    private String originalCladeSchool;
    @CsvBindByName(column = "mapped")
    private String mapped;
    @CsvBindByName(column = "rewritten_name", required = true)
    private String rewrittenName;
    @CsvBindByName(column = "tested_name")
    private String testedName;
    @CsvBindByName(column = "name")
    // @CsvBindByPosition(position = 0)
    private String taxonName;
    @CsvBindByName(column = "txn_name")
    // @CsvBindByPosition(position = 1)
    private String parentName;
    @CsvBindByName(column = "txn_seqno")
    // @CsvBindByPosition(position = 2)
    private String parentSeqno;
    @CsvBindByName(column = "originators_code")
    private String originatorsCode;
    @CsvBindByName(column = "tll_name")
    private String level;
    @CsvBindByName(column = "tll_seqno")
    private String levelSeqno;
    @CsvBindByName(column = "csl_code")
    private String cladeSchoolCode;
    @CsvBindByName(column = "csl_seqno")
    private String cladeSchoolSeqno;
    @CsvBindByName(column = "clade_type")
    private String cladeType;
    @CsvBindByName(column = "txn_name_off")
    private String offTaxon;
    @CsvBindByName(column = "txn_seqno_off")
    private String offTaxonSeqno;
    @CsvBindByName(column = "txn_order")
    private String txnOrder;
    @CsvBindByName(column = "off_publication")
    private String offPublication;
    @CsvBindByName(column = "trivial_name")
    private String trivialName;
    @CsvBindByName(column = "parent_off_name")
    private String offTaxonParent;

    public String getTaxonName() {
        return taxonName;
    }

    public void setTaxonName(String taxonName) {
        this.taxonName = taxonName;
    }

    public String getParentName() {
        return parentName;
    }

    public void setParentName(String parentName) {
        this.parentName = parentName;
    }

    public String getParentSeqno() {
        return parentSeqno;
    }

    public void setParentSeqno(String parentSeqno) {
        this.parentSeqno = parentSeqno;
    }

    public String getOriginatorsCode() {
        return originatorsCode;
    }

    public void setOriginatorsCode(String originatorsCode) {
        this.originatorsCode = originatorsCode;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getLevelSeqno() {
        return levelSeqno;
    }

    public void setLevelSeqno(String levelSeqno) {
        this.levelSeqno = levelSeqno;
    }

    public String getCladeSchoolCode() {
        return cladeSchoolCode;
    }

    public void setCladeSchoolCode(String cladeSchoolCode) {
        this.cladeSchoolCode = cladeSchoolCode;
    }

    public String getCladeSchoolSeqno() {
        return cladeSchoolSeqno;
    }

    public void setCladeSchoolSeqno(String cladeSchoolSeqno) {
        this.cladeSchoolSeqno = cladeSchoolSeqno;
    }

    public String getCladeType() {
        return cladeType;
    }

    public void setCladeType(String cladeType) {
        this.cladeType = cladeType;
    }

    public String getOffTaxon() {
        return offTaxon;
    }

    public void setOffTaxon(String offTaxon) {
        this.offTaxon = offTaxon;
    }

    public String getOffTaxonSeqno() {
        return offTaxonSeqno;
    }

    public void setOffTaxonSeqno(String offTaxonSeqno) {
        this.offTaxonSeqno = offTaxonSeqno;
    }

    public String getTxnOrder() {
        return txnOrder;
    }

    public void setTxnOrder(String txnOrder) {
        this.txnOrder = txnOrder;
    }

    public String getOffPublication() {
        return offPublication;
    }

    public void setOffPublication(String offPublication) {
        this.offPublication = offPublication;
    }

    public String getTrivialName() {
        return trivialName;
    }

    public void setTrivialName(String trivialName) {
        this.trivialName = trivialName;
    }

    public String getOffTaxonParent() {
        return offTaxonParent;
    }

    public void setOffTaxonParent(String offTaxonParent) {
        this.offTaxonParent = offTaxonParent;
    }

    public String getOriginalName() {
        return originalName;
    }

    public void setOriginalName(String originalName) {
        this.originalName = originalName;
    }

    public String getOriginalIdentifier() {
        return originalIdentifier;
    }

    public void setOriginalIdentifier(String originalIdentifier) {
        this.originalIdentifier = originalIdentifier;
    }

    public String getMapped() {
        return mapped;
    }

    public void setMapped(String mapped) {
        this.mapped = mapped;
    }

    public String getTestedName() {
        return testedName;
    }

    public void setTestedName(String testedName) {
        this.testedName = testedName;
    }

    public String getRewrittenName() {
        return rewrittenName;
    }

    public void setRewrittenName(String rewrittenName) {
        this.rewrittenName = rewrittenName;
    }

    public String getOriginalCladeSchool() {
        return originalCladeSchool;
    }

    public void setOriginalCladeSchool(String originalCladeSchool) {
        this.originalCladeSchool = originalCladeSchool;
    }

    public String[] toArray() throws IllegalAccessException {
        String strings[] = new String[HEADER.size()];
        int i = 0;
        for (String headerCell : HEADER) {
            for (Field f : this.getClass().getDeclaredFields()) {
                CsvBindByName column = f.getAnnotation(CsvBindByName.class);
                if (column != null && column.column().equals(headerCell)) {
                    String actualValue = (String) f.get(this);
                    strings[i] = actualValue;
                }
            }
            i++;
        }
        return strings;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 61 * hash + Objects.hashCode(this.taxonName);
        hash = 61 * hash + Objects.hashCode(this.parentName);
        hash = 61 * hash + Objects.hashCode(this.offTaxon);
        hash = 61 * hash + Objects.hashCode(this.offTaxonParent);
        return hash;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        } else if (other instanceof CSVIDODImportEntry) {
            CSVIDODImportEntry otherResultEntry = (CSVIDODImportEntry) other;
            if (getTaxonName() == null && getParentName() == null && getOffTaxon() == null && getOffTaxonParent() == null && otherResultEntry.getTaxonName() == null && otherResultEntry.getParentName() == null && otherResultEntry.getOffTaxon() == null && otherResultEntry.getOffTaxonParent() == null) {
                return getOriginalName().equals(otherResultEntry.getOriginalName());
            }
            return ObjectUtils.equals(getTaxonName(), otherResultEntry.getTaxonName())
                    && ObjectUtils.equals(getParentName(), otherResultEntry.getParentName())
                    && ObjectUtils.equals(getOffTaxon(), otherResultEntry.getOffTaxon())
                    && ObjectUtils.equals(getOffTaxonParent(), otherResultEntry.getOffTaxonParent());

        } else {
            return false;
        }
    }

}
