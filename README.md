# About

Small inventory management system with Java 17

# Running

This program requires Java 17 to run.

To compile and run the program, run the following commands in the root directory:

```sh
javac ./src/Main.java
java ./src/Main
```

To remove the compiled files (`.class` files), run the following command in the root directory:

- Windows
    ```sh
    Get-ChildItem *.class -Recurse | foreach { Remove-Item -Path $_.FullName }
    ```

- macOS or Unix-based operating systems
    ```sh
    find ./src -type f -name '*.class' -delete
    ```