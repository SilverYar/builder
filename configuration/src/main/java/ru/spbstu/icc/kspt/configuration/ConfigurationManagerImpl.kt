package ru.spbstu.icc.kspt.configuration

import ru.spbstu.icc.kspt.ActionImpl
import ru.spbstu.icc.kspt.ModelImpl
import ru.spbstu.icc.kspt.RoleImpl
import ru.spbstu.icc.kspt.ScenarioImpl
import ru.spbstu.icc.kspt.model.Model
import ru.spbstu.icc.kspt.configuration.ConfigurationManager
import java.io.File

class ConfigurationManagerImpl : ConfigurationManager {
    override fun loadModel(file: File): Model {
        return ModelImpl().apply {
            val hunter = RoleImpl("Bruno")
            val rabbit1 = RoleImpl("Mike")
            val rabbit2 = RoleImpl("April")
            val everyOne = RoleImpl("every one")
            addRole(hunter)
            addRole(rabbit1)
            addRole(rabbit2)
            addAction(ActionImpl(rabbit1, rabbit2, text = """
                Rabbits, opens their eyes.
                Choose a place in which the hunter will not find us.
                Moves his rabbit marker and closes his eyes.
            """.trimIndent()))
            addAction(ActionImpl(hunter, text = """
                Hunter, opens his eyes.
                Choose the place where the rabbit is.
                Moves his bullet marker and closes his eyes.
            """.trimIndent()))
            addAction(ActionImpl(everyOne, text = """
                Everyone, opened their eyes.
            """.trimIndent()))
            addAction(ActionImpl(rabbit1, rabbit2, text = """
                Rabbits, check their holes.
                If we have a bullet marker in self hole, then we will dead and lose the game.
            """.trimIndent()))
        }
    }

    override fun getScenario(model: Model) = ScenarioImpl(model.getActions())
}