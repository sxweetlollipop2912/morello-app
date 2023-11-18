DROP TABLE IF EXISTS users;
DROP TABLE IF EXISTS groups;
DROP TABLE IF EXISTS group_leaders;
DROP TABLE IF EXISTS group_moderators;
DROP TABLE IF EXISTS collect_sessions;
DROP TABLE IF EXISTS collect_session_entries;

CREATE TABLE users (
    username TEXT PRIMARY KEY NOT NULL UNIQUE,
    password TEXT NOT NULL,
    email TEXT NOT NULL UNIQUE,
    first_name TEXT NOT NULL,
    last_name TEXT NOT NULL,
    date_joined TEXT NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE groups (
    name TEXT NOT NULL,
    description TEXT NOT NULL,
    date_created TEXT NOT NULL DEFAULT CURRENT_TIMESTAMP,
    creator TEXT NOT NULL,
    FOREIGN KEY (creator) REFERENCES users (username)
    PRIMARY KEY (name, creator)
);

CREATE TABLE group_moderators (
    group_name TEXT NOT NULL,
    group_creator TEXT NOT NULL,
    moderator TEXT NOT NULL,
    date_added TEXT NOT NULL DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (moderator) REFERENCES users (username),
    FOREIGN KEY (group_name, group_creator) REFERENCES groups (name, creator),
    PRIMARY KEY (group_name, group_creator, moderator)
);

CREATE TABLE collect_sessions (
    id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL UNIQUE,
    name TEXT NOT NULL,
    description TEXT NOT NULL,
    date_created TEXT NOT NULL DEFAULT CURRENT_TIMESTAMP,
    due_date TEXT NOT NULL,
    creator TEXT NOT NULL,
    group_id INTEGER NOT NULL,
    FOREIGN KEY (creator) REFERENCES users (username),
    FOREIGN KEY (group_id) REFERENCES groups (id)
);

CREATE TABLE collect_session_entries (
    session_id INTEGER NOT NULL,
    member TEXT NOT NULL,
    paid BOOLEAN NOT NULL DEFAULT FALSE,
    FOREIGN KEY (session_id) REFERENCES collect_sessions (id),
    PRIMARY KEY (session_id, member)
);

