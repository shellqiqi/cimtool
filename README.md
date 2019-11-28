# cimtool

Convert between [libgdx](https://github.com/libgdx/libgdx) defined CIM image format to PNG.

## Usage

Run in cmd or powershell.

```
java -jar cimtool-<version>.jar <Filepath>
```

Automatically convert CIM file with suffix ".cim" to PNG file with suffix ".png", and convert PNG file to CIM file.

I recommand to use bash or powershell which gives the wildcard arguments.

```sh
java -jar cimtool-<version>.jar *.cim
java -jar cimtool-<version>.jar *.png
```
