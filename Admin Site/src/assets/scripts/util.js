import {store} from "../../store/store";

function isObject(obj, keyName, map) {
    if (obj === null)
        return false;
    try {
        return obj.constructor === Object;
    } catch {
        console.log({ obj, keyName, map });
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

export function similarity(s1, s2) {
    let longer = s1;
    let shorter = s2;
    if (s1.length < s2.length) {
        longer = s2;
        shorter = s1;
    }
    const longerLength = longer.length;
    if (longerLength === 0) {
        return 1.0;
    }
    return (
        (longerLength - editDistance(longer, shorter)) /
        parseFloat(longerLength)
    );
}

function editDistance(s1, s2) {
    s1 = s1.toLowerCase();
    s2 = s2.toLowerCase();

    const costs = [];
    for (let i = 0; i <= s1.length; i++) {
        let lastValue = i;
        for (let j = 0; j <= s2.length; j++) {
            if (i === 0) costs[j] = j;
            else {
                if (j > 0) {
                    let newValue = costs[j - 1];
                    if (s1.charAt(i - 1) !== s2.charAt(j - 1))
                        newValue =
                            Math.min(Math.min(newValue, lastValue), costs[j]) +
                            1;
                    costs[j - 1] = lastValue;
                    lastValue = newValue;
                }
            }
        }
        if (i > 0) costs[s2.length] = lastValue;
    }
    return costs[s2.length];
}