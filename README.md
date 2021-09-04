# nullvehicles
Plugin for minecraft spigot 1.12.2 - spawnable entitys by command for donators

# How working: 
Player spawn entity by command and auto-riding on this entity. If he dismount - entity dispawn.


# Commands:
/vehicles - return message in chat with all vehicles
/vehicle `<name>` - spawn vehicle

# Config:
`NoPermission` - Message when user doesnt have permission
`NoArgs` - Message for invalid arguments
`GroundError` - Message when player is flying
`OnlyOne` - Message if player abuse system (only one vehicle limit)
`Spawned` - Message when vehicle spawn
`VehicleList` - Message for /vehicles
`Vehicles` - A list with entity class name

# Permissions:
`nullvehicles.vehicles` - for command /vehicles
`nullvehicles.<vehicle_class_name>` - for summon /vehicle
