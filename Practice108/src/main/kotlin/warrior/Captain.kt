package warrior

import weapon.Weapons

class Captain : AbstractWarrior(
    maxHealth = 150,
    dodgeChance = 30,
    accuracy = 70,
    weapon = Weapons.createRifle()
)