/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package be.naturalsciences.bmdc.mapper.places.model;

import be.naturalsciences.bmdc.mapper.general.MatchType;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A class that represents a geonames.org location
 *
 * @author thomas
 */
public class GeoName implements GazetteerPlace {

    public static final Map<String, String> geoNamesDic = new HashMap<>();
    public static final Map<String, String> synonymousTypesDic = new HashMap<>();

    static {

        synonymousTypesDic.put("ADM1", "ADMD");
        synonymousTypesDic.put("ADM2", "ADMD");
        synonymousTypesDic.put("ADM3", "ADMD");
        synonymousTypesDic.put("ADM4", "ADMD");
        synonymousTypesDic.put("ADM5", "ADMD");

        synonymousTypesDic.put("ADM1H", "ADMDH");
        synonymousTypesDic.put("ADM2H", "ADMDH");
        synonymousTypesDic.put("ADM3H", "ADMDH");
        synonymousTypesDic.put("ADM4H", "ADMDH");

        synonymousTypesDic.put("PPLA", "PPL");
        synonymousTypesDic.put("PPLA2", "PPL");
        synonymousTypesDic.put("PPLA3", "PPL");
        synonymousTypesDic.put("PPLA4", "PPL");
        synonymousTypesDic.put("PPLAC", "PPL");

        geoNamesDic.put("ADM1", "first-order administrative division");
        geoNamesDic.put("ADM1H", "historical first-order administrative division");
        geoNamesDic.put("ADM2", "second-order administrative division");
        geoNamesDic.put("ADM2H", "historical second-order administrative division");
        geoNamesDic.put("ADM3", "third-order administrative division");
        geoNamesDic.put("ADM3H", "historical third-order administrative division");
        geoNamesDic.put("ADM4", "fourth-order administrative division");
        geoNamesDic.put("ADM4H", "historical fourth-order administrative division");
        geoNamesDic.put("ADM5", "fifth-order administrative division");
        geoNamesDic.put("ADMD", "administrative division");
        geoNamesDic.put("ADMDH", "historical administrative division ");
        geoNamesDic.put("LTER", "leased area");
        geoNamesDic.put("PCL", "political entity");
        geoNamesDic.put("PCLD", "dependent political entity");
        geoNamesDic.put("PCLF", "freely associated state");
        geoNamesDic.put("PCLH", "historical political entity");
        geoNamesDic.put("PCLI", "independent political entity");
        geoNamesDic.put("PCLIX", "section of independent political entity");
        geoNamesDic.put("PCLS", "semi-independent political entity");
        geoNamesDic.put("PRSH", "parish");
        geoNamesDic.put("TERR", "territory");
        geoNamesDic.put("ZN", "zone");
        geoNamesDic.put("ZNB", "buffer zone");
        geoNamesDic.put("AIRS", "seaplane landing area");
        geoNamesDic.put("ANCH", "anchorage");
        geoNamesDic.put("BAY", "bay");
        geoNamesDic.put("BAYS", "bays");
        geoNamesDic.put("BGHT", "bight(s)");
        geoNamesDic.put("BNK", "bank(s)");
        geoNamesDic.put("BNKR", "stream bank");
        geoNamesDic.put("BNKX", "section of bank");
        geoNamesDic.put("BOG", "bog(s)");
        geoNamesDic.put("CAPG", "icecap");
        geoNamesDic.put("CHN", "channel");
        geoNamesDic.put("CHNL", "lake channel(s)");
        geoNamesDic.put("CHNM", "marine channel");
        geoNamesDic.put("CHNN", "navigation channel");
        geoNamesDic.put("CNFL", "confluence");
        geoNamesDic.put("CNL", "canal");
        geoNamesDic.put("CNLA", "aqueduct");
        geoNamesDic.put("CNLB", "canal bend");
        geoNamesDic.put("CNLD", "drainage canal");
        geoNamesDic.put("CNLI", "irrigation canal");
        geoNamesDic.put("CNLN", "navigation canal(s)");
        geoNamesDic.put("CNLQ", "abandoned canal");
        geoNamesDic.put("CNLSB", "underground irrigation canal(s)");
        geoNamesDic.put("CNLX", "section of canal");
        geoNamesDic.put("COVE", "cove(s)");
        geoNamesDic.put("CRKT", "tidal creek(s)");
        geoNamesDic.put("CRNT", "current");
        geoNamesDic.put("CUTF", "cutoff");
        geoNamesDic.put("DCK", "dock(s)");
        geoNamesDic.put("DCKB", "docking basin");
        geoNamesDic.put("DOMG", "icecap dome");
        geoNamesDic.put("DPRG", "icecap depression");
        geoNamesDic.put("DTCH", "ditch");
        geoNamesDic.put("DTCHD", "drainage ditch");
        geoNamesDic.put("DTCHI", "irrigation ditch");
        geoNamesDic.put("DTCHM", "ditch mouth(s)");
        geoNamesDic.put("ESTY", "estuary");
        geoNamesDic.put("FISH", "fishing area");
        geoNamesDic.put("FJD", "fjord");
        geoNamesDic.put("FJDS", "fjords");
        geoNamesDic.put("FLLS", "waterfall(s)");
        geoNamesDic.put("FLLSX", "section of waterfall(s)");
        geoNamesDic.put("FLTM", "mud flat(s)");
        geoNamesDic.put("FLTT", "tidal flat(s)");
        geoNamesDic.put("GLCR", "glacier(s)");
        geoNamesDic.put("GULF", "gulf");
        geoNamesDic.put("GYSR", "geyser");
        geoNamesDic.put("HBR", "harbor(s)");
        geoNamesDic.put("HBRX", "section of harbor");
        geoNamesDic.put("INLT", "inlet");
        geoNamesDic.put("INLTQ", "former inlet");
        geoNamesDic.put("LBED", "lake bed(s)");
        geoNamesDic.put("LGN", "lagoon");
        geoNamesDic.put("LGNS", "lagoons");
        geoNamesDic.put("LGNX", "section of lagoon");
        geoNamesDic.put("LK", "lake");
        geoNamesDic.put("LKC", "crater lake");
        geoNamesDic.put("LKI", "intermittent lake");
        geoNamesDic.put("LKN", "salt lake");
        geoNamesDic.put("LKNI", "intermittent salt lake");
        geoNamesDic.put("LKO", "oxbow lake");
        geoNamesDic.put("LKOI", "intermittent oxbow lake");
        geoNamesDic.put("LKS", "lakes");
        geoNamesDic.put("LKSB", "underground lake");
        geoNamesDic.put("LKSC", "crater lakes");
        geoNamesDic.put("LKSI", "intermittent lakes");
        geoNamesDic.put("LKSN", "salt lakes");
        geoNamesDic.put("LKSNI", "intermittent salt lakes");
        geoNamesDic.put("LKX", "section of lake");
        geoNamesDic.put("MFGN", "salt evaporation ponds");
        geoNamesDic.put("MGV", "mangrove swamp");
        geoNamesDic.put("MOOR", "moor(s)");
        geoNamesDic.put("MRSH", "marsh(es)");
        geoNamesDic.put("MRSHN", "salt marsh");
        geoNamesDic.put("NRWS", "narrows");
        geoNamesDic.put("OCN", "ocean");
        geoNamesDic.put("OVF", "overfalls");
        geoNamesDic.put("PND", "pond");
        geoNamesDic.put("PNDI", "intermittent pond");
        geoNamesDic.put("PNDN", "salt pond");
        geoNamesDic.put("PNDNI", "intermittent salt pond(s)");
        geoNamesDic.put("PNDS", "ponds");
        geoNamesDic.put("PNDSF", "fishponds");
        geoNamesDic.put("PNDSI", "intermittent ponds");
        geoNamesDic.put("PNDSN", "salt ponds");
        geoNamesDic.put("POOL", "pool(s)");
        geoNamesDic.put("POOLI", "intermittent pool");
        geoNamesDic.put("RCH", "reach");
        geoNamesDic.put("RDGG", "icecap ridge");
        geoNamesDic.put("RDST", "roadstead");
        geoNamesDic.put("RF", "reef(s)");
        geoNamesDic.put("RFC", "coral reef(s)");
        geoNamesDic.put("RFX", "section of reef");
        geoNamesDic.put("RPDS", "rapids");
        geoNamesDic.put("RSV", "reservoir(s)");
        geoNamesDic.put("RSVI", "intermittent reservoir");
        geoNamesDic.put("RSVT", "water tank");
        geoNamesDic.put("RVN", "ravine(s)");
        geoNamesDic.put("SBKH", "sabkha(s)");
        geoNamesDic.put("SD", "sound");
        geoNamesDic.put("SEA", "sea");
        geoNamesDic.put("SHOL", "shoal(s)");
        geoNamesDic.put("SILL", "sill");
        geoNamesDic.put("SPNG", "spring(s)");
        geoNamesDic.put("SPNS", "sulphur spring(s)");
        geoNamesDic.put("SPNT", "hot spring(s)");
        geoNamesDic.put("STM", "stream");
        geoNamesDic.put("STMA", "anabranch");
        geoNamesDic.put("STMB", "stream bend");
        geoNamesDic.put("STMC", "canalized stream");
        geoNamesDic.put("STMD", "distributary(-ies)");
        geoNamesDic.put("STMH", "headwaters");
        geoNamesDic.put("STMI", "intermittent stream");
        geoNamesDic.put("STMIX", "section of intermittent stream");
        geoNamesDic.put("STMM", "stream mouth(s)");
        geoNamesDic.put("STMQ", "abandoned watercourse");
        geoNamesDic.put("STMS", "streams");
        geoNamesDic.put("STMSB", "lost river");
        geoNamesDic.put("STMX", "section of stream");
        geoNamesDic.put("STRT", "strait");
        geoNamesDic.put("SWMP", "swamp");
        geoNamesDic.put("SYSI", "irrigation system");
        geoNamesDic.put("TNLC", "canal tunnel");
        geoNamesDic.put("WAD", "wadi");
        geoNamesDic.put("WADB", "wadi bend");
        geoNamesDic.put("WADJ", "wadi junction");
        geoNamesDic.put("WADM", "wadi mouth");
        geoNamesDic.put("WADS", "wadies");
        geoNamesDic.put("WADX", "section of wadi");
        geoNamesDic.put("WHRL", "whirlpool");
        geoNamesDic.put("WLL", "well");
        geoNamesDic.put("WLLQ", "abandoned well");
        geoNamesDic.put("WLLS", "wells");
        geoNamesDic.put("WTLD", "wetland");
        geoNamesDic.put("WTLDI", "intermittent wetland");
        geoNamesDic.put("WTRC", "watercourse");
        geoNamesDic.put("WTRH", "waterhole(s)");
        geoNamesDic.put("AGRC", "agricultural colony");
        geoNamesDic.put("AMUS", "amusement park");
        geoNamesDic.put("AREA", "area");
        geoNamesDic.put("BSND", "drainage basin");
        geoNamesDic.put("BSNP", "petroleum basin");
        geoNamesDic.put("BTL", "battlefield");
        geoNamesDic.put("CLG", "clearing");
        geoNamesDic.put("CMN", "common");
        geoNamesDic.put("CNS", "concession area");
        geoNamesDic.put("COLF", "coalfield");
        geoNamesDic.put("CONT", "continent");
        geoNamesDic.put("CST", "coast");
        geoNamesDic.put("CTRB", "business center");
        geoNamesDic.put("DEVH", "housing development");
        geoNamesDic.put("FLD", "field(s)");
        geoNamesDic.put("FLDI", "irrigated field(s)");
        geoNamesDic.put("GASF", "gasfield");
        geoNamesDic.put("GRAZ", "grazing area");
        geoNamesDic.put("GVL", "gravel area");
        geoNamesDic.put("INDS", "industrial area");
        geoNamesDic.put("LAND", "arctic land");
        geoNamesDic.put("LCTY", "locality");
        geoNamesDic.put("MILB", "military base");
        geoNamesDic.put("MNA", "mining area");
        geoNamesDic.put("MVA", "maneuver area");
        geoNamesDic.put("NVB", "naval base");
        geoNamesDic.put("OAS", "oasis(-es)");
        geoNamesDic.put("OILF", "oilfield");
        geoNamesDic.put("PEAT", "peat cutting area");
        geoNamesDic.put("PRK", "park");
        geoNamesDic.put("PRT", "port");
        geoNamesDic.put("QCKS", "quicksand");
        geoNamesDic.put("RES", "reserve");
        geoNamesDic.put("RESA", "agricultural reserve");
        geoNamesDic.put("RESF", "forest reserve");
        geoNamesDic.put("RESH", "hunting reserve");
        geoNamesDic.put("RESN", "nature reserve");
        geoNamesDic.put("RESP", "palm tree reserve");
        geoNamesDic.put("RESV", "reservation");
        geoNamesDic.put("RESW", "wildlife reserve");
        geoNamesDic.put("RGN", "region");
        geoNamesDic.put("RGNE", "economic region");
        geoNamesDic.put("RGNH", "historical region");
        geoNamesDic.put("RGNL", "lake region");
        geoNamesDic.put("RNGA", "artillery range");
        geoNamesDic.put("SALT", "salt area");
        geoNamesDic.put("SNOW", "snowfield");
        geoNamesDic.put("TRB", "tribal area");
        geoNamesDic.put("PPL", "populated place");
        geoNamesDic.put("PPLA", "seat of a first-order administrative division");
        geoNamesDic.put("PPLA2", "seat of a second-order administrative division");
        geoNamesDic.put("PPLA3", "seat of a third-order administrative division");
        geoNamesDic.put("PPLA4", "seat of a fourth-order administrative division");
        geoNamesDic.put("PPLC", "capital of a political entity");
        geoNamesDic.put("PPLCH", "historical capital of a political entity");
        geoNamesDic.put("PPLF", "farm village");
        geoNamesDic.put("PPLG", "seat of government of a political entity");
        geoNamesDic.put("PPLH", "historical populated place");
        geoNamesDic.put("PPLL", "populated locality");
        geoNamesDic.put("PPLQ", "abandoned populated place");
        geoNamesDic.put("PPLR", "religious populated place");
        geoNamesDic.put("PPLS", "populated places");
        geoNamesDic.put("PPLW", "destroyed populated place");
        geoNamesDic.put("PPLX", "section of populated place");
        geoNamesDic.put("STLMT", "israeli settlement");
        geoNamesDic.put("CSWY", "causeway");
        geoNamesDic.put("OILP", "oil pipeline");
        geoNamesDic.put("PRMN", "promenade");
        geoNamesDic.put("PTGE", "portage");
        geoNamesDic.put("RD", "road");
        geoNamesDic.put("RDA", "ancient road");
        geoNamesDic.put("RDB", "road bend");
        geoNamesDic.put("RDCUT", "road cut");
        geoNamesDic.put("RDJCT", "road junction");
        geoNamesDic.put("RJCT", "railroad junction");
        geoNamesDic.put("RR", "railroad");
        geoNamesDic.put("RRQ", "abandoned railroad");
        geoNamesDic.put("RTE", "caravan route");
        geoNamesDic.put("RYD", "railroad yard");
        geoNamesDic.put("ST", "street");
        geoNamesDic.put("STKR", "stock route");
        geoNamesDic.put("TNL", "tunnel");
        geoNamesDic.put("TNLN", "natural tunnel");
        geoNamesDic.put("TNLRD", "road tunnel");
        geoNamesDic.put("TNLRR", "railroad tunnel");
        geoNamesDic.put("TNLS", "tunnels");
        geoNamesDic.put("TRL", "trail");
        geoNamesDic.put("ADMF", "administrative facility");
        geoNamesDic.put("AGRF", "agricultural facility");
        geoNamesDic.put("AIRB", "airbase");
        geoNamesDic.put("AIRF", "airfield");
        geoNamesDic.put("AIRH", "heliport");
        geoNamesDic.put("AIRP", "airport");
        geoNamesDic.put("AIRQ", "abandoned airfield");
        geoNamesDic.put("AMTH", "amphitheater");
        geoNamesDic.put("ANS", "ancient site");
        geoNamesDic.put("AQC", "aquaculture facility");
        geoNamesDic.put("ARCH", "arch");
        geoNamesDic.put("ASTR", "astronomical station");
        geoNamesDic.put("ASYL", "asylum");
        geoNamesDic.put("ATHF", "athletic field");
        geoNamesDic.put("ATM", "automatic teller machine");
        geoNamesDic.put("BANK", "bank");
        geoNamesDic.put("BCN", "beacon");
        geoNamesDic.put("BDG", "bridge");
        geoNamesDic.put("BDGQ", "ruined bridge");
        geoNamesDic.put("BLDG", "building(s)");
        geoNamesDic.put("BLDO", "office building");
        geoNamesDic.put("BP", "boundary marker");
        geoNamesDic.put("BRKS", "barracks");
        geoNamesDic.put("BRKW", "breakwater");
        geoNamesDic.put("BSTN", "baling station");
        geoNamesDic.put("BTYD", "boatyard");
        geoNamesDic.put("BUR", "burial cave(s)");
        geoNamesDic.put("BUSTN", "bus station");
        geoNamesDic.put("BUSTP", "bus stop");
        geoNamesDic.put("CARN", "cairn");
        geoNamesDic.put("CAVE", "cave(s)");
        geoNamesDic.put("CH", "church");
        geoNamesDic.put("CMP", "camp(s)");
        geoNamesDic.put("CMPL", "logging camp");
        geoNamesDic.put("CMPLA", "labor camp");
        geoNamesDic.put("CMPMN", "mining camp");
        geoNamesDic.put("CMPO", "oil camp");
        geoNamesDic.put("CMPQ", "abandoned camp");
        geoNamesDic.put("CMPRF", "refugee camp");
        geoNamesDic.put("CMTY", "cemetery");
        geoNamesDic.put("COMC", "communication center");
        geoNamesDic.put("CRRL", "corral(s)");
        geoNamesDic.put("CSNO", "casino");
        geoNamesDic.put("CSTL", "castle");
        geoNamesDic.put("CSTM", "customs house");
        geoNamesDic.put("CTHSE", "courthouse");
        geoNamesDic.put("CTRA", "atomic center");
        geoNamesDic.put("CTRCM", "community center");
        geoNamesDic.put("CTRF", "facility center");
        geoNamesDic.put("CTRM", "medical center");
        geoNamesDic.put("CTRR", "religious center");
        geoNamesDic.put("CTRS", "space center");
        geoNamesDic.put("CVNT", "convent");
        geoNamesDic.put("DAM", "dam");
        geoNamesDic.put("DAMQ", "ruined dam");
        geoNamesDic.put("DAMSB", "sub-surface dam");
        geoNamesDic.put("DARY", "dairy");
        geoNamesDic.put("DCKD", "dry dock");
        geoNamesDic.put("DCKY", "dockyard");
        geoNamesDic.put("DIKE", "dike");
        geoNamesDic.put("DIP", "diplomatic facility");
        geoNamesDic.put("DPOF", "fuel depot");
        geoNamesDic.put("EST", "estate(s)");
        geoNamesDic.put("ESTO", "oil palm plantation");
        geoNamesDic.put("ESTR", "rubber plantation");
        geoNamesDic.put("ESTSG", "sugar plantation");
        geoNamesDic.put("ESTT", "tea plantation");
        geoNamesDic.put("ESTX", "section of estate");
        geoNamesDic.put("FCL", "facility");
        geoNamesDic.put("FNDY", "foundry");
        geoNamesDic.put("FRM", "farm");
        geoNamesDic.put("FRMQ", "abandoned farm");
        geoNamesDic.put("FRMS", "farms");
        geoNamesDic.put("FRMT", "farmstead");
        geoNamesDic.put("FT", "fort");
        geoNamesDic.put("FY", "ferry");
        geoNamesDic.put("GATE", "gate");
        geoNamesDic.put("GDN", "garden(s)");
        geoNamesDic.put("GHAT", "ghat");
        geoNamesDic.put("GHSE", "guest house");
        geoNamesDic.put("GOSP", "gas-oil separator plant");
        geoNamesDic.put("GOVL", "local government office");
        geoNamesDic.put("GRVE", "grave");
        geoNamesDic.put("HERM", "hermitage");
        geoNamesDic.put("HLT", "halting place");
        geoNamesDic.put("HMSD", "homestead");
        geoNamesDic.put("HSE", "house(s)");
        geoNamesDic.put("HSEC", "country house");
        geoNamesDic.put("HSP", "hospital");
        geoNamesDic.put("HSPC", "clinic");
        geoNamesDic.put("HSPD", "dispensary");
        geoNamesDic.put("HSPL", "leprosarium");
        geoNamesDic.put("HSTS", "historical site");
        geoNamesDic.put("HTL", "hotel");
        geoNamesDic.put("HUT", "hut");
        geoNamesDic.put("HUTS", "huts");
        geoNamesDic.put("INSM", "military installation");
        geoNamesDic.put("ITTR", "research institute");
        geoNamesDic.put("JTY", "jetty");
        geoNamesDic.put("LDNG", "landing");
        geoNamesDic.put("LEPC", "leper colony");
        geoNamesDic.put("LIBR", "library");
        geoNamesDic.put("LNDF", "landfill");
        geoNamesDic.put("LOCK", "lock(s)");
        geoNamesDic.put("LTHSE", "lighthouse");
        geoNamesDic.put("MALL", "mall");
        geoNamesDic.put("MAR", "marina");
        geoNamesDic.put("MFG", "factory");
        geoNamesDic.put("MFGB", "brewery");
        geoNamesDic.put("MFGC", "cannery");
        geoNamesDic.put("MFGCU", "copper works");
        geoNamesDic.put("MFGLM", "limekiln");
        geoNamesDic.put("MFGM", "munitions plant");
        geoNamesDic.put("MFGPH", "phosphate works");
        geoNamesDic.put("MFGQ", "abandoned factory");
        geoNamesDic.put("MFGSG", "sugar refinery");
        geoNamesDic.put("MKT", "market");
        geoNamesDic.put("ML", "mill(s)");
        geoNamesDic.put("MLM", "ore treatment plant");
        geoNamesDic.put("MLO", "olive oil mill");
        geoNamesDic.put("MLSG", "sugar mill");
        geoNamesDic.put("MLSGQ", "former sugar mill");
        geoNamesDic.put("MLSW", "sawmill");
        geoNamesDic.put("MLWND", "windmill");
        geoNamesDic.put("MLWTR", "water mill");
        geoNamesDic.put("MN", "mine(s)");
        geoNamesDic.put("MNAU", "gold mine(s)");
        geoNamesDic.put("MNC", "coal mine(s)");
        geoNamesDic.put("MNCR", "chrome mine(s)");
        geoNamesDic.put("MNCU", "copper mine(s)");
        geoNamesDic.put("MNFE", "iron mine(s)");
        geoNamesDic.put("MNMT", "monument");
        geoNamesDic.put("MNN", "salt mine(s)");
        geoNamesDic.put("MNQ", "abandoned mine");
        geoNamesDic.put("MNQR", "quarry(-ies)");
        geoNamesDic.put("MOLE", "mole");
        geoNamesDic.put("MSQE", "mosque");
        geoNamesDic.put("MSSN", "mission");
        geoNamesDic.put("MSSNQ", "abandoned mission");
        geoNamesDic.put("MSTY", "monastery");
        geoNamesDic.put("MTRO", "metro station");
        geoNamesDic.put("MUS", "museum");
        geoNamesDic.put("NOV", "novitiate");
        geoNamesDic.put("NSY", "nursery(-ies)");
        geoNamesDic.put("OBPT", "observation point");
        geoNamesDic.put("OBS", "observatory");
        geoNamesDic.put("OBSR", "radio observatory");
        geoNamesDic.put("OILJ", "oil pipeline junction");
        geoNamesDic.put("OILQ", "abandoned oil well");
        geoNamesDic.put("OILR", "oil refinery");
        geoNamesDic.put("OILT", "tank farm");
        geoNamesDic.put("OILW", "oil well");
        geoNamesDic.put("OPRA", "opera house");
        geoNamesDic.put("PAL", "palace");
        geoNamesDic.put("PGDA", "pagoda");
        geoNamesDic.put("PIER", "pier");
        geoNamesDic.put("PKLT", "parking lot");
        geoNamesDic.put("PMPO", "oil pumping station");
        geoNamesDic.put("PMPW", "water pumping station");
        geoNamesDic.put("PO", "post office");
        geoNamesDic.put("PP", "police post");
        geoNamesDic.put("PPQ", "abandoned police post");
        geoNamesDic.put("PRKGT", "park gate");
        geoNamesDic.put("PRKHQ", "park headquarters");
        geoNamesDic.put("PRN", "prison");
        geoNamesDic.put("PRNJ", "reformatory");
        geoNamesDic.put("PRNQ", "abandoned prison");
        geoNamesDic.put("PS", "power station");
        geoNamesDic.put("PSH", "hydroelectric power station");
        geoNamesDic.put("PSTB", "border post");
        geoNamesDic.put("PSTC", "customs post");
        geoNamesDic.put("PSTP", "patrol post");
        geoNamesDic.put("PYR", "pyramid");
        geoNamesDic.put("PYRS", "pyramids");
        geoNamesDic.put("QUAY", "quay");
        geoNamesDic.put("RDCR", "traffic circle");
        geoNamesDic.put("RECG", "golf course");
        geoNamesDic.put("RECR", "racetrack");
        geoNamesDic.put("REST", "restaurant");
        geoNamesDic.put("RET", "store");
        geoNamesDic.put("RHSE", "resthouse");
        geoNamesDic.put("RKRY", "rookery");
        geoNamesDic.put("RLG", "religious site");
        geoNamesDic.put("RLGR", "retreat");
        geoNamesDic.put("RNCH", "ranch(es)");
        geoNamesDic.put("RSD", "railroad siding");
        geoNamesDic.put("RSGNL", "railroad signal");
        geoNamesDic.put("RSRT", "resort");
        geoNamesDic.put("RSTN", "railroad station");
        geoNamesDic.put("RSTNQ", "abandoned railroad station");
        geoNamesDic.put("RSTP", "railroad stop");
        geoNamesDic.put("RSTPQ", "abandoned railroad stop");
        geoNamesDic.put("RUIN", "ruin(s)");
        geoNamesDic.put("SCH", "school");
        geoNamesDic.put("SCHA", "agricultural school");
        geoNamesDic.put("SCHC", "college");
        geoNamesDic.put("SCHL", "language school");
        geoNamesDic.put("SCHM", "military school");
        geoNamesDic.put("SCHN", "maritime school");
        geoNamesDic.put("SCHT", "technical school");
        geoNamesDic.put("SECP", "State Exam Prep Centre");
        geoNamesDic.put("SHPF", "sheepfold");
        geoNamesDic.put("SHRN", "shrine");
        geoNamesDic.put("SHSE", "storehouse");
        geoNamesDic.put("SLCE", "sluice");
        geoNamesDic.put("SNTR", "sanatorium");
        geoNamesDic.put("SPA", "spa");
        geoNamesDic.put("SPLY", "spillway");
        geoNamesDic.put("SQR", "square");
        geoNamesDic.put("STBL", "stable");
        geoNamesDic.put("STDM", "stadium");
        geoNamesDic.put("STNB", "scientific research base");
        geoNamesDic.put("STNC", "coast guard station");
        geoNamesDic.put("STNE", "experiment station");
        geoNamesDic.put("STNF", "forest station");
        geoNamesDic.put("STNI", "inspection station");
        geoNamesDic.put("STNM", "meteorological station");
        geoNamesDic.put("STNR", "radio station");
        geoNamesDic.put("STNS", "satellite station");
        geoNamesDic.put("STNW", "whaling station");
        geoNamesDic.put("STPS", "steps");
        geoNamesDic.put("SWT", "sewage treatment plant");
        geoNamesDic.put("THTR", "theater");
        geoNamesDic.put("TMB", "tomb(s)");
        geoNamesDic.put("TMPL", "temple(s)");
        geoNamesDic.put("TNKD", "cattle dipping tank");
        geoNamesDic.put("TOWR", "tower");
        geoNamesDic.put("TRANT", "transit terminal");
        geoNamesDic.put("TRIG", "triangulation station");
        geoNamesDic.put("TRMO", "oil pipeline terminal");
        geoNamesDic.put("TWO", "temp work office");
        geoNamesDic.put("UNIP", "university prep school");
        geoNamesDic.put("UNIV", "university");
        geoNamesDic.put("USGE", "united states government establishment");
        geoNamesDic.put("VETF", "veterinary facility");
        geoNamesDic.put("WALL", "wall");
        geoNamesDic.put("WALLA", "ancient wall");
        geoNamesDic.put("WEIR", "weir(s)");
        geoNamesDic.put("WHRF", "wharf(-ves)");
        geoNamesDic.put("WRCK", "wreck");
        geoNamesDic.put("WTRW", "waterworks");
        geoNamesDic.put("ZNF", "free trade zone");
        geoNamesDic.put("ZOO", "zoo");
        geoNamesDic.put("ASPH", "asphalt lake");
        geoNamesDic.put("ATOL", "atoll(s)");
        geoNamesDic.put("BAR", "bar");
        geoNamesDic.put("BCH", "beach");
        geoNamesDic.put("BCHS", "beaches");
        geoNamesDic.put("BDLD", "badlands");
        geoNamesDic.put("BLDR", "boulder field");
        geoNamesDic.put("BLHL", "blowhole(s)");
        geoNamesDic.put("BLOW", "blowout(s)");
        geoNamesDic.put("BNCH", "bench");
        geoNamesDic.put("BUTE", "butte(s)");
        geoNamesDic.put("CAPE", "cape");
        geoNamesDic.put("CFT", "cleft(s)");
        geoNamesDic.put("CLDA", "caldera");
        geoNamesDic.put("CLF", "cliff(s)");
        geoNamesDic.put("CNYN", "canyon");
        geoNamesDic.put("CONE", "cone(s)");
        geoNamesDic.put("CRDR", "corridor");
        geoNamesDic.put("CRQ", "cirque");
        geoNamesDic.put("CRQS", "cirques");
        geoNamesDic.put("CRTR", "crater(s)");
        geoNamesDic.put("CUET", "cuesta(s)");
        geoNamesDic.put("DLTA", "delta");
        geoNamesDic.put("DPR", "depression(s)");
        geoNamesDic.put("DSRT", "desert");
        geoNamesDic.put("DUNE", "dune(s)");
        geoNamesDic.put("DVD", "divide");
        geoNamesDic.put("ERG", "sandy desert");
        geoNamesDic.put("FAN", "fan(s)");
        geoNamesDic.put("FORD", "ford");
        geoNamesDic.put("FSR", "fissure");
        geoNamesDic.put("GAP", "gap");
        geoNamesDic.put("GRGE", "gorge(s)");
        geoNamesDic.put("HDLD", "headland");
        geoNamesDic.put("HLL", "hill");
        geoNamesDic.put("HLLS", "hills");
        geoNamesDic.put("HMCK", "hammock(s)");
        geoNamesDic.put("HMDA", "rock desert");
        geoNamesDic.put("INTF", "interfluve");
        geoNamesDic.put("ISL", "island");
        geoNamesDic.put("ISLET", "islet");
        geoNamesDic.put("ISLF", "artificial island");
        geoNamesDic.put("ISLM", "mangrove island");
        geoNamesDic.put("ISLS", "islands");
        geoNamesDic.put("ISLT", "land-tied island");
        geoNamesDic.put("ISLX", "section of island");
        geoNamesDic.put("ISTH", "isthmus");
        geoNamesDic.put("KRST", "karst area");
        geoNamesDic.put("LAVA", "lava area");
        geoNamesDic.put("LEV", "levee");
        geoNamesDic.put("MESA", "mesa(s)");
        geoNamesDic.put("MND", "mound(s)");
        geoNamesDic.put("MRN", "moraine");
        geoNamesDic.put("MT", "mountain");
        geoNamesDic.put("MTS", "mountains");
        geoNamesDic.put("NKM", "meander neck");
        geoNamesDic.put("NTK", "nunatak");
        geoNamesDic.put("NTKS", "nunataks");
        geoNamesDic.put("PAN", "pan");
        geoNamesDic.put("PANS", "pans");
        geoNamesDic.put("PASS", "pass");
        geoNamesDic.put("PEN", "peninsula");
        geoNamesDic.put("PENX", "section of peninsula");
        geoNamesDic.put("PK", "peak");
        geoNamesDic.put("PKS", "peaks");
        geoNamesDic.put("PLAT", "plateau");
        geoNamesDic.put("PLATX", "section of plateau");
        geoNamesDic.put("PLDR", "polder");
        geoNamesDic.put("PLN", "plain(s)");
        geoNamesDic.put("PLNX", "section of plain");
        geoNamesDic.put("PROM", "promontory(-ies)");
        geoNamesDic.put("PT", "point");
        geoNamesDic.put("PTS", "points");
        geoNamesDic.put("RDGB", "beach ridge");
        geoNamesDic.put("RDGE", "ridge(s)");
        geoNamesDic.put("REG", "stony desert");
        geoNamesDic.put("RK", "rock");
        geoNamesDic.put("RKFL", "rockfall");
        geoNamesDic.put("RKS", "rocks");
        geoNamesDic.put("SAND", "sand area");
        geoNamesDic.put("SBED", "dry stream bed");
        geoNamesDic.put("SCRP", "escarpment");
        geoNamesDic.put("SDL", "saddle");
        geoNamesDic.put("SHOR", "shore");
        geoNamesDic.put("SINK", "sinkhole");
        geoNamesDic.put("SLID", "slide");
        geoNamesDic.put("SLP", "slope(s)");
        geoNamesDic.put("SPIT", "spit");
        geoNamesDic.put("SPUR", "spur(s)");
        geoNamesDic.put("TAL", "talus slope");
        geoNamesDic.put("TRGD", "interdune trough(s)");
        geoNamesDic.put("TRR", "terrace");
        geoNamesDic.put("UPLD", "upland");
        geoNamesDic.put("VAL", "valley");
        geoNamesDic.put("VALG", "hanging valley");
        geoNamesDic.put("VALS", "valleys");
        geoNamesDic.put("VALX", "section of valley");
        geoNamesDic.put("VLC", "volcano");
        geoNamesDic.put("APNU", "apron");
        geoNamesDic.put("ARCU", "arch");
        geoNamesDic.put("ARRU", "arrugado");
        geoNamesDic.put("BDLU", "borderland");
        geoNamesDic.put("BKSU", "banks");
        geoNamesDic.put("BNKU", "bank");
        geoNamesDic.put("BSNU", "basin");
        geoNamesDic.put("CDAU", "cordillera");
        geoNamesDic.put("CNSU", "canyons");
        geoNamesDic.put("CNYU", "canyon");
        geoNamesDic.put("CRSU", "continental rise");
        geoNamesDic.put("DEPU", "deep");
        geoNamesDic.put("EDGU", "shelf edge");
        geoNamesDic.put("ESCU", "escarpment (or scarp)");
        geoNamesDic.put("FANU", "fan");
        geoNamesDic.put("FLTU", "flat");
        geoNamesDic.put("FRZU", "fracture zone");
        geoNamesDic.put("FURU", "furrow");
        geoNamesDic.put("GAPU", "gap");
        geoNamesDic.put("GLYU", "gully");
        geoNamesDic.put("HLLU", "hill");
        geoNamesDic.put("HLSU", "hills");
        geoNamesDic.put("HOLU", "hole");
        geoNamesDic.put("KNLU", "knoll");
        geoNamesDic.put("KNSU", "knolls");
        geoNamesDic.put("LDGU", "ledge");
        geoNamesDic.put("LEVU", "levee");
        geoNamesDic.put("MESU", "mesa");
        geoNamesDic.put("MNDU", "mound");
        geoNamesDic.put("MOTU", "moat");
        geoNamesDic.put("MTU", "mountain");
        geoNamesDic.put("PKSU", "peaks");
        geoNamesDic.put("PKU", "peak");
        geoNamesDic.put("PLNU", "plain");
        geoNamesDic.put("PLTU", "plateau");
        geoNamesDic.put("PNLU", "pinnacle");
        geoNamesDic.put("PRVU", "province");
        geoNamesDic.put("RDGU", "ridge");
        geoNamesDic.put("RDSU", "ridges");
        geoNamesDic.put("RFSU", "reefs");
        geoNamesDic.put("RFU", "reef");
        geoNamesDic.put("RISU", "rise");
        geoNamesDic.put("SCNU", "seachannel");
        geoNamesDic.put("SCSU", "seachannels");
        geoNamesDic.put("SDLU", "saddle");
        geoNamesDic.put("SHFU", "shelf");
        geoNamesDic.put("SHLU", "shoal");
        geoNamesDic.put("SHSU", "shoals");
        geoNamesDic.put("SHVU", "shelf valley");
        geoNamesDic.put("SILU", "sill");
        geoNamesDic.put("SLPU", "slope");
        geoNamesDic.put("SMSU", "seamounts");
        geoNamesDic.put("SMU", "seamount");
        geoNamesDic.put("SPRU", "spur");
        geoNamesDic.put("TERU", "terrace");
        geoNamesDic.put("TMSU", "tablemounts (or guyots)");
        geoNamesDic.put("TMTU", "tablemount (or guyot)");
        geoNamesDic.put("TNGU", "tongue");
        geoNamesDic.put("TRGU", "trough");
        geoNamesDic.put("TRNU", "trench");
        geoNamesDic.put("VALU", "valley");
        geoNamesDic.put("VLSU", "valleys");
        geoNamesDic.put("BUSH", "bush(es)");
        geoNamesDic.put("CULT", "cultivated area");
        geoNamesDic.put("FRST", "forest(s)");
        geoNamesDic.put("FRSTF", "fossilized forest");
        geoNamesDic.put("GRSLD", "grassland");
        geoNamesDic.put("GRVC", "coconut grove");
        geoNamesDic.put("GRVO", "olive grove");
        geoNamesDic.put("GRVP", "palm grove");
        geoNamesDic.put("GRVPN", "pine grove");
        geoNamesDic.put("HTH", "heath");
        geoNamesDic.put("MDW", "meadow");
        geoNamesDic.put("OCH", "orchard(s)");
        geoNamesDic.put("SCRB", "scrubland");
        geoNamesDic.put("TREE", "tree(s)");
        geoNamesDic.put("TUND", "tundra");
        geoNamesDic.put("VIN", "vineyard");
        geoNamesDic.put("VINS", "vineyards");
    }
    private String toponymName;
    private String name;
    private double lat;
    private double lng;
    private Integer geonameId;
    private String countryCode;
    private String fcl;
    private String fcode;
    private String countryId;
    private MatchType matchType;
    /**
     * *
     *
     */
    private int numberOfResults;

    public GeoName(String name, Integer geonameId, String countryCode, String type, int hitList) {
        this.name = name;
        this.geonameId = geonameId;
        this.countryCode = countryCode;
        this.fcode = type;
        this.numberOfResults = hitList;
    }

    public String getToponymName() {
        return toponymName;
    }

    public void setToponymName(String toponymName) {
        this.toponymName = toponymName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }

    public Integer getGeonameId() {
        return geonameId;
    }

    public void setGeonameId(Integer geonameId) {
        this.geonameId = geonameId;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public String getFcl() {
        return fcl;
    }

    public void setFcl(String fcl) {
        this.fcl = fcl;
    }

    public String getFcode() {
        return fcode;
    }

    public void setFcode(String fcode) {
        this.fcode = fcode;
    }

    @Override
    public String getLocalName() {
        return getToponymName();
    }

    @Override
    public void setLocalName(String localName) {
        setToponymName(localName);
    }

    @Override
    public Integer getId() {
        return getGeonameId();
    }

    @Override
    public void setId(Integer id) {
        setGeonameId(id);
    }

    @Override
    public String getType() {
        return getFcode();
    }

    @Override
    public void setType(String type) {
        setFcode(type);
    }

    @Override
    public String getStatus() {
        return "standard";
    }

    @Override
    public void setStatus(String status) {

    }

    @Override
    public int getNumberOfResults() {
        return numberOfResults;
    }

    @Override
    public void setNumberOfResults(int numberOfResults) {
        this.numberOfResults = numberOfResults;
    }

    @Override
    public Object getSource() {
        return "geonames.org";
    }

    @Override
    public Integer getCountryId() {
        return countryId != null ? Integer.parseInt(countryId) : null;
    }

    @Override
    public void setCountryId(Integer countryId) {
        this.countryId = Integer.toString(countryId);
    }

    @Override
    public MatchType getMatchType() {
        return matchType;
    }

    @Override
    public void setMatchType(MatchType matchType) {
        this.matchType = matchType;
    }

    public String toString() {
        return getId() + ": " + getName() + " (country: " + getCountryCode() + "; localName: " + getLocalName() + ";type: " + getType() + "; match type: " + getMatchType().name() + ") ";
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        } else {
            GeoName otherMR = (GeoName) other;
            return this.getId().equals(otherMR.getId());
        }
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 67 * hash + this.geonameId;
        return hash;
    }

    public boolean nameIsFeatureType(String name) {
        return geoNamesDic.values().stream().map(s -> s.toLowerCase()).anyMatch(s -> s.equals(name.toLowerCase()));
    }

    /**
     * *
     * Test whether types broadly match. This means that : The same types match
     * Types sharing the same superType match Types and supertypes match
     *
     * @param otherType
     * @return
     */
    public boolean typeMatchesType(String otherType) {
        String ownType = this.getType();
        if (ownType != null && ownType.equals(otherType)) {
            return true;
        }
        String otherParentType = synonymousTypesDic.get(otherType);
        if (otherParentType != null && ownType != null) {
            boolean r1 = false;
            for (Map.Entry<String, String> entry : synonymousTypesDic.entrySet()) {
                String key = entry.getKey();
                String value = entry.getValue();
                if (value.equals(otherParentType) && key.equals(ownType)) {
                    r1 = true;
                }
            }

            return r1 || (synonymousTypesDic.get(otherType) != null ? synonymousTypesDic.get(otherType) : "").toLowerCase().equals(this.getType().toLowerCase()) || (synonymousTypesDic.get(this.getType()) != null ? synonymousTypesDic.get(this.getType()) : "").toLowerCase().equals(otherType.toLowerCase());
        }
        return false;
    }

    @Override
    public List<String> listFieldKeys() {
        return Arrays.asList("gazetteer id", "gazetteer name", "gazetteer type", "string match type", "type match type", "number of results", "latitude", "longitude", "ISO 3166-1 country");
    }

    @Override
    public List<String> listFieldValues() {
        return Arrays.asList(Integer.toString(getId()), getName(), getType(), getMatchType().getTextualMatchType().name(), getMatchType().getSemanticMatchType().name(), Integer.toString(getNumberOfResults()), Double.toString(getLat()), Double.toString(getLng()), getCountryCode());
    }
}
