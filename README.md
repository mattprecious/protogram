# Protogram

Protogram is a tool to quickly deserialize and print an encoded proto without the need for any `.proto` files.

## Command-Line Usage

Pass a hex-encoded proto message to `protogram`:

```
$ protogram 0a0344616e120911000000000000e03f70107a021001
┌─  1: "Dan"
├─  2 ┐
│     ╰- 2: 4602678819172646912 (0.5)
├─ 14: 16
╰- 15 ┐
      ╰- 2: 1
```

You can also pipe a binary file into `protogram`:

```
$ cat dan.pb | protogram
┌─  1: "Dan"
├─  2 ┐
│     ╰- 2: 4602678819172646912 (0.5)
├─ 14: 16
╰- 15 ┐
      ╰- 2: 1
```

If you have the proto files available, `protogram` can parse the names more accurately:

```
protogram --source protos/ --type example.User 0a0344616e120911000000000000e03f70107a021001
┌─  name: "Dan"
├─ prefs: ┐
│         ╰- ratio: 4602678819172646912 (0.5)
├─   age: 16
╰- count: ┐
          ╰- download: 1
```

## Browser Usage

The same code that powers the command-line tool is compiled to JS and lives at
https://mattprecious.github.io/protogram/. Enter hex-encoded protos or open binary proto
files from your computer.

## Download

Available in [Releases](https://github.com/mattprecious/protogram/releases) or via Homebrew:

```bash
brew install mattprecious/repo/protogram
```

## License

```
Copyright 2019 Matthew Precious

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

   http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```
