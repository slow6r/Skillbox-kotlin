package warrior

import weapon.Weapons

class General : AbstractWarrior(
    maxHealth = 200,
    dodgeChance = 40,
    accuracy = 80,
    weapon = Weapons.createRocketLauncher()
)