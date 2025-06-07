# Jenny Mod Standalone Game

This project contains the original Jenny Mod code along with a `standalone` module
that launches a very small LWJGL window. The module is a starting point for
turning the mod into its own game.

## Building

Run `gradle build` from the `standalone` directory or from the project root if
you have the Gradle wrapper installed.

### Windows EXE

You can create a Windows executable using the `launch4j` plugin. Simply run
`gradle createExe` inside the `standalone` directory. The build copies
`JennyModGame.exe` into a `compiled/` folder at the project root (this folder
is ignored by Git), while the raw Launch4j output stays in
`standalone/build/launch4j/`. If you just need to copy an existing build
again, you can run `gradle packageExe` instead. The executable itself is not
checked into the repository to avoid committing large binary files.

## Running

Use `gradle run` in the `standalone` directory. The task attempts to open a
GLFW window, so it requires an environment with a graphical display.
