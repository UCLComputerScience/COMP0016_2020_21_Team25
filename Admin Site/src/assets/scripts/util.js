import {store} from "../../store/store";

function isObject(obj, keyName, map) {
    if (obj === null)
        return false;
    try {
        return obj.constructor === Object;
    } catch {
        console.log({ obj, keyName, map })
        return false;
    }
}

export function toKebabCaseMap(map) {
    const formattedMap = {};
    for (const [key, value] of Object.entries(map)) {
        formattedMap[toKebabCase(key)] = (isObject(value, key, map)) ? toKebabCaseMap(value) : value;
    }
    return formattedMap;
}

export function toSnakeCaseMap(map) {
    const formattedMap = {};
    for (const [key, value] of Object.entries(map)) {
        formattedMap[toSnakeCase(key)] = (isObject(value, key, map)) ? toSnakeCaseMap(value) : value;
    }
    return formattedMap;
}

export const toSnakeCase = (str) => {
    const formatted = str.replaceAll(/[A-Z]/g, letter => `_${letter.toLowerCase()}`);
    return formatted.replaceAll("-", "_");
};

export const toKebabCase = (str) => {
    return str
        .split("")
        .map((letter, idx) => {
            if (isNaN(letter)) {
                return letter.toUpperCase() === letter
                    ? `${idx !== 0 ? "-" : ""}${letter.toLowerCase()}`
                    : letter;
            }
            return letter;
        })
        .join("").replaceAll("_", "").replaceAll("--", "-");
};

export function getName() {
    const firstName = store.getters["member/activeMember"]["first-name"];
    if (firstName.endsWith("s")) return firstName + "'";
    return firstName + "'s";
}

export const getServiceIcon = (name) => {
    return store.getters["media/serviceIcons"][name];
};

export const getProfileImage = (id) => {
    const name = store.getters["media/profileIds"][id];
    return store.getters["media/profileImages"][name];
};
