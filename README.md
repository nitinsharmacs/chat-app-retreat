# chat-app-retreat

Chat application for E4R Retreat.

## Motivation

The motivation behind this small PoC is to explore [Armeria](https://armeria.dev/).

## Run

```
git clone https://github.com/nitinsharmacs/chat-app-retreat.git

cd chat-app-retreat

sbt compile "~run"

cd frontend
npm install
npm run dev
```

Open `http://localhost:8080`.

## Scope

It is a minimilistic PoC chat application written in scala/java on backend and typescript/react on frontend.

It doesn't use any database. It stores data in memory that eventually goes once the app is destroyed.

It doesn't either use websockets for communication. It uses polling to get updates.
