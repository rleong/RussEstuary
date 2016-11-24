package framework;

public enum ObjectId {
	// Old Stuff
	recycle(), trashBin(), recycleBin(), trash(), compost(),

	// Unimplemented
	waterWaste(), 

	// Drop Items
	oyster(), concreteSlab, seed(),

	// Enemies
	bird(), fish(),

	// Environment
	land(), landSurface(), habitat(), seaLevel(), sand(),

	water(), waterSurface(), waterTree(), 

	// Hazards
	waves(), runOff(), waste(), bubble(),

	// Waste Deposits
	wasteBin(),
	
	// Barriers
	tree(), wall(),
	
	// Hazard Spawners
	clock(), boat(), RofFactory(),

	// Player
	inventory(), critter();

}
