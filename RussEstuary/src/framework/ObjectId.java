package framework;

public enum ObjectId {
	// Old Stuff
	recycle(), trashBin(), recycleBin(), trash(), compost(),

	// Unimplemented
	waterWaste(),

	// Drop Items
	oyster(), concreteSlab,

	// Enemies
	bird(), fish(),

	// Environment
	land(), landSurface(), habitat(), seaLevel(),

	water(), waterSurface(),

	// Hazards
	waves(), runOff(), waste(),

	// Waste Deposits
	compostCounter(), wasteBin(),
	
	// Barriers
	tree(), wall(),
	
	// Hazard Spawners
	clock(), boat(), RofFactory(),

	// Player
	critter();

}
