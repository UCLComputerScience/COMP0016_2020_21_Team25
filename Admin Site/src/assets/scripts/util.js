import {store} from "../../store/store";

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
    const firstName = store.getters["member/activeMember"].firstName;
    if (firstName.endsWith("s")) return firstName + "'";
    return firstName + "'s";
}

export const getServiceIcon = (name) => {
    return store.getters["media/serviceIcons"][name];
};

export const getProfileImage = (name) => {
    return store.getters["media/profileImages"][name];
};
