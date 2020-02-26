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
 * A class that represents VLIZ' Marine Regions
 *
 * @author thomas
 */
public class MarineRegion implements GazetteerPlace {

    public static final Map<String, String> synonymousTypesDic = new HashMap<>();
    public static final Map<String, String> marineRegionsDic = new HashMap();

    static {
        marineRegionsDic.put("Town", "Town");
        marineRegionsDic.put("Arrondissement", "Arrondissement");
        marineRegionsDic.put("Department", "Department");
        marineRegionsDic.put("Province (administrative)", "Province (administrative)");
        marineRegionsDic.put("Country", "Country");
        marineRegionsDic.put("Continent", "Continent");
        marineRegionsDic.put("Region", "Region");
        marineRegionsDic.put("Ward", "Ward");
        marineRegionsDic.put("Commune", "Commune");
        marineRegionsDic.put("District", "District");
        marineRegionsDic.put("Canton", "Canton");
        marineRegionsDic.put("Sub-Province", "Sub-Province");
        marineRegionsDic.put("Nation", "Nation");
        marineRegionsDic.put("County", "County");
        marineRegionsDic.put("Unitary Authority", "Unitary Authority");
        marineRegionsDic.put("Borough", "Borough");
        marineRegionsDic.put("World", "World");
        marineRegionsDic.put("Ocean", "Ocean");
        marineRegionsDic.put("Sea", "Sea");
        marineRegionsDic.put("Dependent State", "Dependent State");
        marineRegionsDic.put("Island", "Island");
        marineRegionsDic.put("Former Nation", "Former Nation");
        marineRegionsDic.put("Gulf", "Gulf");
        marineRegionsDic.put("Basin", "Basin");
        marineRegionsDic.put("Current", "Current");
        marineRegionsDic.put("Water mass", "Water mass");
        marineRegionsDic.put("Strait", "Strait");
        marineRegionsDic.put("Sandbank", "Sandbank");
        marineRegionsDic.put("Ridge", "Ridge");
        marineRegionsDic.put("Channel", "Channel");
        marineRegionsDic.put("Front", "Front");
        marineRegionsDic.put("Bight", "Bight");
        marineRegionsDic.put("Field", "Field");
        marineRegionsDic.put("Ground", "Ground");
        marineRegionsDic.put("Sandbank System", "Sandbank System");
        marineRegionsDic.put("Deep", "Deep");
        marineRegionsDic.put("Plateau", "Plateau");
        marineRegionsDic.put("Bay", "Bay");
        marineRegionsDic.put("Island Group", "Island Group");
        marineRegionsDic.put("Archipelago", "Archipelago");
        marineRegionsDic.put("Deelgemeente", "Deelgemeente");
        marineRegionsDic.put("Lake", "Lake");
        marineRegionsDic.put("River", "River");
        marineRegionsDic.put("Lagoon", "Lagoon");
        marineRegionsDic.put("Fjord", "Fjord");
        marineRegionsDic.put("General Region", "General Region");
        marineRegionsDic.put("Stream", "Stream");
        marineRegionsDic.put("Estuary", "Estuary");
        marineRegionsDic.put("Swale", "Swale");
        marineRegionsDic.put("Canal", "Canal");
        marineRegionsDic.put("Fracture Zone", "Fracture Zone");
        marineRegionsDic.put("Sound", "Sound");
        marineRegionsDic.put("Seamount(s)", "Seamount(s)");
        marineRegionsDic.put("Canyon", "Canyon");
        marineRegionsDic.put("Trough", "Trough");
        marineRegionsDic.put("Spur", "Spur");
        marineRegionsDic.put("Slope", "Slope");
        marineRegionsDic.put("Hill(s)", "Hill(s)");
        marineRegionsDic.put("Plain", "Plain");
        marineRegionsDic.put("Caldera", "Caldera");
        marineRegionsDic.put("Guyot", "Guyot");
        marineRegionsDic.put("Abyssal Plain", "Abyssal Plain");
        marineRegionsDic.put("Coast", "Coast");
        marineRegionsDic.put("EEZ", "EEZ");
        marineRegionsDic.put("Inhabited Place", "Inhabited Place");
        marineRegionsDic.put("Inlet", "Inlet");
        marineRegionsDic.put("Hole", "Hole");
        marineRegionsDic.put("General Sea Area", "General Sea Area");
        marineRegionsDic.put("Delta", "Delta");
        marineRegionsDic.put("State", "State");
        marineRegionsDic.put("Bank", "Bank");
        marineRegionsDic.put("Seachannel", "Seachannel");
        marineRegionsDic.put("Escarpment", "Escarpment");
        marineRegionsDic.put("Valley", "Valley");
        marineRegionsDic.put("Knoll(s)", "Knoll(s)");
        marineRegionsDic.put("Fan", "Fan");
        marineRegionsDic.put("Seamount Chain", "Seamount Chain");
        marineRegionsDic.put("Reef", "Reef");
        marineRegionsDic.put("Rise", "Rise");
        marineRegionsDic.put("Trench", "Trench");
        marineRegionsDic.put("Continental Shelf", "Continental Shelf");
        marineRegionsDic.put("Oil Field", "Oil Field");
        marineRegionsDic.put("Gas Field", "Gas Field");
        marineRegionsDic.put("Autonomous Region", "Autonomous Region");
        marineRegionsDic.put("Apron", "Apron");
        marineRegionsDic.put("Borderland", "Borderland");
        marineRegionsDic.put("Continental Margin", "Continental Margin");
        marineRegionsDic.put("Levee", "Levee");
        marineRegionsDic.put("Moat", "Moat");
        marineRegionsDic.put("Passage", "Passage");
        marineRegionsDic.put("Peak", "Peak");
        marineRegionsDic.put("Pinnacle", "Pinnacle");
        marineRegionsDic.put("Promontory", "Promontory");
        marineRegionsDic.put("Saddle", "Saddle");
        marineRegionsDic.put("Shelf Edge", "Shelf Edge");
        marineRegionsDic.put("Shoal", "Shoal");
        marineRegionsDic.put("Sill", "Sill");
        marineRegionsDic.put("Terrace", "Terrace");
        marineRegionsDic.put("Dike", "Dike");
        marineRegionsDic.put("Mud Flat", "Mud Flat");
        marineRegionsDic.put("Salt Marsh", "Salt Marsh");
        marineRegionsDic.put("Wad", "Wad");
        marineRegionsDic.put("Polder", "Polder");
        marineRegionsDic.put("Harbour", "Harbour");
        marineRegionsDic.put("Dock", "Dock");
        marineRegionsDic.put("Sluice", "Sluice");
        marineRegionsDic.put("Continental Slope", "Continental Slope");
        marineRegionsDic.put("Submarine lava tube", "Submarine lava tube");
        marineRegionsDic.put("Cave", "Cave");
        marineRegionsDic.put("Natural Reserve", "Natural Reserve");
        marineRegionsDic.put("Marine Park", "Marine Park");
        marineRegionsDic.put("Cape", "Cape");
        marineRegionsDic.put("Sampling Station", "Sampling Station");
        marineRegionsDic.put("Peninsula", "Peninsula");
        marineRegionsDic.put("Flat", "Flat");
        marineRegionsDic.put("Cliffs", "Cliffs");
        marineRegionsDic.put("Dunes", "Dunes");
        marineRegionsDic.put("Former administrative division", "Former administrative division");
        marineRegionsDic.put("Reservoir", "Reservoir");
        marineRegionsDic.put("National Park", "National Park");
        marineRegionsDic.put("National District", "National District");
        marineRegionsDic.put("Mountain range", "Mountain range");
        marineRegionsDic.put("Land basin", "Land basin");
        marineRegionsDic.put("Gap", "Gap");
        marineRegionsDic.put("Seamount Province", "Seamount Province");
        marineRegionsDic.put("Tablemount", "Tablemount");
        marineRegionsDic.put("Zone", "Zone");
        marineRegionsDic.put("Shelf", "Shelf");
        marineRegionsDic.put("Large Marine Ecosystem", "Large Marine Ecosystem");
        marineRegionsDic.put("Disputed Territory", "Disputed Territory");
        marineRegionsDic.put("Union Territory", "Union Territory");
        marineRegionsDic.put("Possession", "Possession");
        marineRegionsDic.put("Territory", "Territory");
        marineRegionsDic.put("Occupied Territory", "Occupied Territory");
        marineRegionsDic.put("Dependency", "Dependency");
        marineRegionsDic.put("Overseas Territory", "Overseas Territory");
        marineRegionsDic.put("National Division", "National Division");
        marineRegionsDic.put("Rock", "Rock");
        marineRegionsDic.put("Light(house)", "Light(house)");
        marineRegionsDic.put("Beach", "Beach");
        marineRegionsDic.put("FAO fishing area", "FAO fishing area");
        marineRegionsDic.put("Pit", "Pit");
        marineRegionsDic.put("Plate", "Plate");
        marineRegionsDic.put("Atoll", "Atoll");
        marineRegionsDic.put("Parish", "Parish");
        marineRegionsDic.put("Prefecture", "Prefecture");
        marineRegionsDic.put("Controlled flood zone", "Controlled flood zone");
        marineRegionsDic.put("Bird Directive Area", "Bird Directive Area");
        marineRegionsDic.put("Glacier", "Glacier");
        marineRegionsDic.put("Point", "Point");
        marineRegionsDic.put("Cove", "Cove");
        marineRegionsDic.put("Mount", "Mount");
        marineRegionsDic.put("Sea floor", "Sea floor");
        marineRegionsDic.put("Nunatak", "Nunatak");
        marineRegionsDic.put("Nunataks", "Nunataks");
        marineRegionsDic.put("Snowfield", "Snowfield");
        marineRegionsDic.put("Heights", "Heights");
        marineRegionsDic.put("Gate", "Gate");
        marineRegionsDic.put("Anchorage", "Anchorage");
        marineRegionsDic.put("Buttress", "Buttress");
        marineRegionsDic.put("Pass", "Pass");
        marineRegionsDic.put("Crag", "Crag");
        marineRegionsDic.put("Bluff", "Bluff");
        marineRegionsDic.put("Gully", "Gully");
        marineRegionsDic.put("Creek", "Creek");
        marineRegionsDic.put("Icefall", "Icefall");
        marineRegionsDic.put("Col", "Col");
        marineRegionsDic.put("Base", "Base");
        marineRegionsDic.put("Rocks", "Rocks");
        marineRegionsDic.put("Diving spot", "Diving spot");
        marineRegionsDic.put("City", "City");
        marineRegionsDic.put("Oblast", "Oblast");
        marineRegionsDic.put("Spit", "Spit");
        marineRegionsDic.put("Headland", "Headland");
        marineRegionsDic.put("Village", "Village");
        marineRegionsDic.put("Firth", "Firth");
        marineRegionsDic.put("Marsh(es)", "Marsh(es)");
        marineRegionsDic.put("Roadstead", "Roadstead");
        marineRegionsDic.put("Swamp", "Swamp");
        marineRegionsDic.put("Isthmus", "Isthmus");
        marineRegionsDic.put("Lowland", "Lowland");
        marineRegionsDic.put("Arm", "Arm");
        marineRegionsDic.put("Entrance", "Entrance");
        marineRegionsDic.put("Depression", "Depression");
        marineRegionsDic.put("Stack(s)", "Stack(s)");
        marineRegionsDic.put("Subglacial basin", "Subglacial basin");
        marineRegionsDic.put("Rim", "Rim");
        marineRegionsDic.put("Station", "Station");
        marineRegionsDic.put("Tongue", "Tongue");
        marineRegionsDic.put("Ice Shelf", "Ice Shelf");
        marineRegionsDic.put("Ice Sheet", "Ice Sheet");
        marineRegionsDic.put("Wreck", "Wreck");
        marineRegionsDic.put("Fort", "Fort");
        marineRegionsDic.put("Beacon", "Beacon");
        marineRegionsDic.put("Tower", "Tower");
        marineRegionsDic.put("River Outlet", "River Outlet");
        marineRegionsDic.put("Camping", "Camping");
        marineRegionsDic.put("Resort", "Resort");
        marineRegionsDic.put("Protected Area", "Protected Area");
        marineRegionsDic.put("Research Station", "Research Station");
        marineRegionsDic.put("Wetland", "Wetland");
        marineRegionsDic.put("Municipality", "Municipality");
        marineRegionsDic.put("Mangrove", "Mangrove");
        marineRegionsDic.put("Volcano", "Volcano");
        marineRegionsDic.put("Spring", "Spring");
        marineRegionsDic.put("Watsonian vice-county", "Watsonian vice-county");
        marineRegionsDic.put("Wharf", "Wharf");
        marineRegionsDic.put("Park", "Park");
        marineRegionsDic.put("Floristic Region", "Floristic Region");
        marineRegionsDic.put("Museum", "Museum");
        marineRegionsDic.put("Longhurst Province", "Longhurst Province");
        marineRegionsDic.put("Hydro-electric power station", "Hydro-electric power station");
        marineRegionsDic.put("Realm", "Realm");
        marineRegionsDic.put("Marine Province", "Marine Province");
        marineRegionsDic.put("Marine Ecoregion of the World (MEOW)", "Marine Ecoregion of the World (MEOW)");
        marineRegionsDic.put("ICES Ecoregion", "ICES Ecoregion");
        marineRegionsDic.put("Land", "Land");
        marineRegionsDic.put("Republic", "Republic");
        marineRegionsDic.put("Aquifer", "Aquifer");
        marineRegionsDic.put("NAFO Area", "NAFO Area");
        marineRegionsDic.put("IOS region", "IOS region");
        marineRegionsDic.put("Marine Protected Area (MPA)", "Marine Protected Area (MPA)");
        marineRegionsDic.put("SeaVoX SeaArea - region", "SeaVoX SeaArea - region");
        marineRegionsDic.put("SeaVoX SeaArea - level 1", "SeaVoX SeaArea - level 1");
        marineRegionsDic.put("SeaVoX SeaArea - level 2", "SeaVoX SeaArea - level 2");
        marineRegionsDic.put("SeaVoX SeaArea - level 3", "SeaVoX SeaArea - level 3");
        marineRegionsDic.put("SeaVoX SeaArea - sub-region", "SeaVoX SeaArea - sub-region");
        marineRegionsDic.put("SeaVoX SeaArea - level 4", "SeaVoX SeaArea - level 4");
        marineRegionsDic.put("Historical fishing areas", "Historical fishing areas");
        marineRegionsDic.put("Bridge", "Bridge");
        marineRegionsDic.put("Discordance", "Discordance");
        marineRegionsDic.put("Rift", "Rift");
        marineRegionsDic.put("Mud Volcano", "Mud Volcano");
        marineRegionsDic.put("Marine Region", "Marine Region");
        marineRegionsDic.put("Governorate", "Governorate");
        marineRegionsDic.put("IHO Sea Area", "IHO Sea Area");
        marineRegionsDic.put("Head", "Head");
        marineRegionsDic.put("OSPAR Boundary", "OSPAR Boundary");
        marineRegionsDic.put("World Marine Heritage Site", "World Marine Heritage Site");
        marineRegionsDic.put("Natura 2000 Special Protection Area (SPA, EU Birds Directive)", "Natura 2000 Special Protection Area (SPA, EU Birds Directive)");
        marineRegionsDic.put("Natura 2000 Site of Community Importance (SCI, EU Habitats Directive)", "Natura 2000 Site of Community Importance (SCI, EU Habitats Directive)");
        marineRegionsDic.put("Natura 2000 Special Protection Area and Site of Community Importance (SPA and SCI, EU Birds and Habitats Directive)", "Natura 2000 Special Protection Area and Site of Community Importance (SPA and SCI, EU Birds and Habitats Directive)");
        marineRegionsDic.put("Contourite Depositional System", "Contourite Depositional System");
        marineRegionsDic.put("Drift", "Drift");
        marineRegionsDic.put("Landkreis", "Landkreis");
        marineRegionsDic.put("Maar", "Maar");
        marineRegionsDic.put("Federate state", "Federate state");
        marineRegionsDic.put("Lock", "Lock");
        marineRegionsDic.put("Forest", "Forest");
        marineRegionsDic.put("Boat lift", "Boat lift");
        marineRegionsDic.put("Landscape Protection Area", "Landscape Protection Area");
        marineRegionsDic.put("Area", "Area");
        marineRegionsDic.put("Wetland of International Importance (Ramsar Convention)", "Wetland of International Importance (Ramsar Convention)");
        marineRegionsDic.put("UNESCO-MAB Biosphere Reserve", "UNESCO-MAB Biosphere Reserve");
        marineRegionsDic.put("Pond", "Pond");
        marineRegionsDic.put("Watermill", "Watermill");
        marineRegionsDic.put("Military Domain", "Military Domain");
        marineRegionsDic.put("Waterfall", "Waterfall");
        marineRegionsDic.put("Building, ranch", "Building, ranch");
        marineRegionsDic.put("OSPAR Region", "OSPAR Region");
        marineRegionsDic.put("Cordillera", "Cordillera");
        marineRegionsDic.put("Furrow", "Furrow");
        marineRegionsDic.put("Mesa", "Mesa");
        marineRegionsDic.put("Mound", "Mound");
        marineRegionsDic.put("Mountain(s)", "Mountain(s)");
        marineRegionsDic.put("Province (physical)", "Province (physical)");
        marineRegionsDic.put("Undersea arch", "Undersea arch");
        marineRegionsDic.put("Undersea arrugado", "Undersea arrugado");
        marineRegionsDic.put("Man-made structure", "Man-made structure");
        marineRegionsDic.put("Lower Bathyal Provinces", "Lower Bathyal Provinces");
        marineRegionsDic.put("Abyssal Provinces", "Abyssal Provinces");
        marineRegionsDic.put("FAO Major Marine Fishing Areas", "FAO Major Marine Fishing Areas");
        marineRegionsDic.put("Bar", "Bar");
        marineRegionsDic.put("Breakwater", "Breakwater");
        marineRegionsDic.put("Dam", "Dam");
        marineRegionsDic.put("Hamlet", "Hamlet");
        marineRegionsDic.put("Division", "Division");
        marineRegionsDic.put("Submarine valley(s)", "Submarine valley(s)");
        marineRegionsDic.put("Oil field", "Oil field");
        marineRegionsDic.put("TDWG", "TDWG");
        marineRegionsDic.put("TDWG - level 1", "TDWG - level 1");
        marineRegionsDic.put("TDWG - level 2", "TDWG - level 2");
        marineRegionsDic.put("TDWG - level 3", "TDWG - level 3");
        marineRegionsDic.put("TDWG - level 4", "TDWG - level 4");
        marineRegionsDic.put("ICES Areas", "ICES Areas");
        marineRegionsDic.put("ICES Statistical Rectangles", "ICES Statistical Rectangles");
        marineRegionsDic.put("FADA Faunistic Regions", "FADA Faunistic Regions");
        marineRegionsDic.put("Northwest Atlantic Hydrographic Regions", "Northwest Atlantic Hydrographic Regions");
        marineRegionsDic.put("FAO Subareas", "FAO Subareas");
        marineRegionsDic.put("FAO Divisions", "FAO Divisions");
        marineRegionsDic.put("FAO Subdivisions", "FAO Subdivisions");
        marineRegionsDic.put("Arctic Marine Area", "Arctic Marine Area");
        marineRegionsDic.put("HAB Region (Harmful Algal Bloom, Harmful Algal Information System)", "HAB Region (Harmful Algal Bloom, Harmful Algal Information System)");
        marineRegionsDic.put("Marine Subregion", "Marine Subregion");
        marineRegionsDic.put("Hydrothermal vent", "Hydrothermal vent");
        marineRegionsDic.put("Joint regime (EEZ)", "Joint regime (EEZ)");
        marineRegionsDic.put("Territorial Sea", "Territorial Sea");
        marineRegionsDic.put("Contiguous zone", "Contiguous zone");
        marineRegionsDic.put("Internal waters", "Internal waters");
        marineRegionsDic.put("Archipelagic waters", "Archipelagic waters");
        marineRegionsDic.put("Tributary", "Tributary");
        marineRegionsDic.put("FAO Subunits", "FAO Subunits");
        marineRegionsDic.put("Pingo(s)", "Pingo(s)");
        marineRegionsDic.put("Lobe", "Lobe");
        marineRegionsDic.put("Ledge(s)", "Ledge(s)");
        marineRegionsDic.put("Historical undersea feature", "Historical undersea feature");
        marineRegionsDic.put("MSFD Marine subregions", "MSFD Marine subregions");
        marineRegionsDic.put("MSFD Marine regions", "MSFD Marine regions");
        marineRegionsDic.put("Marine Bioregion", "Marine Bioregion");
        marineRegionsDic.put("Overlapping claim", "Overlapping claim");
        marineRegionsDic.put("Mesopelagic ecoregions", "Mesopelagic ecoregions");
        marineRegionsDic.put("Mineral Rights Areas", "Mineral Rights Areas");
        marineRegionsDic.put("Pierre Noire", "Pierre Noire");
    }

    private int MRGID;
    private String gazetteerSource;
    private String placeType;
    private double latitude;
    private double longitude;
    private double minLatitude;
    private double minLongitude;
    private double maxLatitude;
    private double maxLongitude;
    private double precision;
    private String preferredGazetteerName;
    private String preferredGazetteerNameLang;
    private String status;
    private int accepted;
    private MatchType matchType;
    private int numberOfResults;

    public int getMRGID() {
        return MRGID;
    }

    public void setMRGID(int MRGID) {
        this.MRGID = MRGID;
    }

    public String getGazetteerSource() {
        return gazetteerSource;
    }

    public void setGazetteerSource(String gazetteerSource) {
        this.gazetteerSource = gazetteerSource;
    }

    public String getPlaceType() {
        return placeType;
    }

    public void setPlaceType(String placeType) {
        this.placeType = placeType;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getMinLatitude() {
        return minLatitude;
    }

    public void setMinLatitude(double minLatitude) {
        this.minLatitude = minLatitude;
    }

    public double getMinLongitude() {
        return minLongitude;
    }

    public void setMinLongitude(double minLongitude) {
        this.minLongitude = minLongitude;
    }

    public double getMaxLatitude() {
        return maxLatitude;
    }

    public void setMaxLatitude(double maxLatitude) {
        this.maxLatitude = maxLatitude;
    }

    public double getMaxLongitude() {
        return maxLongitude;
    }

    public void setMaxLongitude(double maxLongitude) {
        this.maxLongitude = maxLongitude;
    }

    public double getPrecision() {
        return precision;
    }

    public void setPrecision(double precision) {
        this.precision = precision;
    }

    public String getPreferredGazetteerName() {
        return preferredGazetteerName;
    }

    public void setPreferredGazetteerName(String preferredGazetteerName) {
        this.preferredGazetteerName = preferredGazetteerName;
    }

    public String getPreferredGazetteerNameLang() {
        return preferredGazetteerNameLang;
    }

    public void setPreferredGazetteerNameLang(String preferredGazetteerNameLang) {
        this.preferredGazetteerNameLang = preferredGazetteerNameLang;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getAccepted() {
        return accepted;
    }

    public void setAccepted(int accepted) {
        this.accepted = accepted;
    }

    public String getName() {
        return getPreferredGazetteerName();
    }

    public void setName(String name) {
        setPreferredGazetteerName(name);
    }

    @Override
    public String getLocalName() {
        return null;
    }

    @Override
    public void setLocalName(String localName) {

    }

    @Override
    public Integer getId() {
        return getMRGID();
    }

    @Override
    public void setId(Integer id) {
        setMRGID(id);
    }

    @Override
    public String getType() {
        return getPlaceType();
    }

    @Override
    public void setType(String type) {
        setPlaceType(type);
    }

    @Override
    public double getLat() {
        return getLatitude();
    }

    @Override
    public void setLat(double lat) {
        setLatitude(lat);
    }

    @Override
    public double getLng() {
        return getLongitude();
    }

    @Override
    public void setLng(double lng) {
        setLongitude(lng);
    }

    @Override
    public String getSource() {
        return getGazetteerSource();
    }

    @Override
    public Integer getCountryId() {
        return null;
    }

    @Override
    public void setCountryId(Integer countryId) {
    }

    @Override
    public String getCountryCode() {
        return null;
    }

    @Override
    public void setCountryCode(String countryCode) {
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
        return getId() + ": " + getName() + " (localName: " + getLocalName() + ";type: " + getType() + "; match type: " + getMatchType().name() + ") ";
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        } else {
            MarineRegion otherMR = (MarineRegion) other;
            return this.getId().equals(otherMR.getId());
        }
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 67 * hash + this.MRGID;
        return hash;
    }

    public boolean nameIsFeatureType(String name) {
        return marineRegionsDic.values().stream().map(s -> s.toLowerCase()).anyMatch(s -> s.equals(name.toLowerCase()));
    }

    public boolean typeMatchesType(String generalType) {
        return false; //not implemented, MRG doesn't have the concept of supertype.
    }

    public int getNumberOfResults() {
        return numberOfResults;
    }

    public void setNumberOfResults(int numberOfResults) {
        this.numberOfResults = numberOfResults;
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
