# dumpfav

Dump your favs as JSON from your Twitter account

## How to use

See `src/main/scala/windymelt/dumpfav/Main.scala`.
You should pass some environmental variables to sbt for OAuth tokens like below:

```sh
#!/bin/bash

# for twitter4s (twitter library)
export TWITTER_CONSUMER_TOKEN_KEY='XXXXXXX'
export TWITTER_CONSUMER_TOKEN_SECRET='YYYYYYYY'
export TWITTER_ACCESS_TOKEN_KEY='ZZZZZ-AAAAAAAA'
export TWITTER_ACCESS_TOKEN_SECRET='BBBBBBBBBB'

exec sbt run
```

`Main.scala` acquires 200 favs at a time, later-to-older order.
`Main.scala` acquires next 200 favs when it runs again.

## Cursor system

`Main.scala` generates *cursor file* and *data file*.

### Cursor file

*Cursor file* records minimum tweet id per acquire.
It is read and replaced every time `Main.scala` runs.

### Data file

*Data file* records your favs.
JSON-formatted, one tweet per line like below:

```json
{ "text": "buzz" }
{ "text": "bar" }
{ "text": "foo" }
...
```

It is appended every time `Main.scala` runs.