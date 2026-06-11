MC-LeashFix
===========

This is a Bukkit plugin to workaround Minecraft bug [MC-307839](https://bugs.mojang.com/browse/MC/issues/MC-307839),
still active as of Minecraft 26.1.2.

Bug explanation
---------------

When wolves are leashed, their NBT `home_pos` and `home_radius` properties are set. When the player
unleashes them, these properties are cleared.

When they are dragged into a nether portal, however, the leash breaks but these properties are not
cleared, making them stop reacting and attacking all other monsters until they are edited with
`/data remove entity @e[type=wolf,limit=1,sort=nearest] home_radius`, or they are leashed and
unleashed by the player.

Workaround
----------

This plugin waits for entity teleportation events, and clears the mob's home position using NMS
methods before a teleportation occurs.
