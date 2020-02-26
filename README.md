# A library to match csv files against vocabulary lists, such as gazetteers and taxonomic backbones. Implemented are GeoNames and WoRMS.

## Get the source or binaries
For nontechnical users, the easiest is to get the release binary and download the properties.yaml.example file.

## Settings
Rename the properties.yaml.example file to properties.yaml and modify the settings accordingly. If not set, only queries without the origin_db switch will work.

## Usage
Currently only configured to work with some hardcoded databases as the queries still form part of the code.

Read a csv file with input taxa and original identifiers:
 
	java -jar gazetteer-vocab-mapper.jar -target worms -origin_db darwin -origin_file /home/thomas/Documents/Project-NaturalHeritage/QC/2020-01_taxa/near_matches_to_get_author_short.csv 

Read the darwin database and limit the query to 50 entries

	java -jar gazetteer-vocab-mapper.jar -target geonames -origin_db darwin -size 50
Sample output for the last query will be:

	Searching on geonames for 'Chaville' (48/50). 
	Searching on geonames for 'Facture' (49/50). 
	Searching on geonames for 'Dongen' (50/50). 
	Reconciling ran for 4 seconds.
	-------------------PRINTING TO FILE-------------------
	Writing to csv: Dongen (type: ADMD): 2756723: Dongen (country: NL; localName: 	Dongen;type: PPL; match type: EXACT_NO_TYPE_MATCH) 
	Writing to csv: Ay (type: PPL): 3035594: Aÿ (country: FR; localName: Ay;type: PPL; match type: EXACT_EXACT_TYPE_MATCH) 
	Writing to csv: Moorsel (type: ADMD): 2790736: Moorsel (country: BE; localName: Moorsel;type: PPL; match type: FIRST_HIT_FIRST_HIT) 
	Writing to csv: Dole (type: PPL): 3021263: Dole (country: FR; localName: Dole;type: PPLA3; match type: EXACT_NO_TYPE_MATCH) ; 3005776: Lavans-lès-Dole (country: FR; localName: Lavans-lès-Dole;type: PPL; match type: PARTIAL_EXACT_TYPE_


