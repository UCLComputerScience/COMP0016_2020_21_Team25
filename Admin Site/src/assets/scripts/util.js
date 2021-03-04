import {store} from "../../store/store";

export function toKebabCaseMap(map) {
    const formattedMap = {};
    for (const [key, value] of Object.entries(map)) {
        formattedMap[toKebabCase(key)] = value;
    }
    return formattedMap;
}

export function toSnakeCaseMap(map) {
    const formattedMap = {};
    for (const [key, value] of Object.entries(map)) {
        formattedMap[toSnakeCase(key)] = value;
    }
    return formattedMap;
}

export const toSnakeCase = (str) => {
    const formatted = str.replace(/[A-Z]/g, letter => `_${letter.toLowerCase()}`);
    return formatted.replace("-", "_");
}

export const toKebabCase = (str) => {
    return str
        .split("")
        .map((letter, idx) => {
            return letter.toUpperCase() === letter
                ? `${idx !== 0 ? "-" : ""}${letter.toLowerCase()}`
                : letter;
        })
        .join("");
};

export function getName() {
    const firstName = store.getters["member/activeMember"]["first-name"];
    if (firstName.endsWith("s")) return firstName + "'";
    return firstName + "'s";
}

export const getServiceIcon = (name) => {
    return store.getters["media/serviceIcons"][name];
};

export const getProfileImage = (name) => {
    return store.getters["media/profileImages"][name];
};
