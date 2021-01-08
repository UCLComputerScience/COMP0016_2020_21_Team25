import {store} from "../../app/store/store";

export const toKebabCase = (str) => {
    return str.split('').map((letter, idx) => {
        return letter.toUpperCase() === letter
            ? `${idx !== 0 ? '-' : ''}${letter.toLowerCase()}`
            : letter;
    }).join('');
}

export function getName() {
    let firstName = store.getters["member/activeMember"].firstName;
    if (firstName.endsWith('s'))
        return firstName + "'";
    return firstName + "'s";
}

export const profileImages = {
    "apples": require(`./../images/profile-pictures/apples.webp`).default,
    "daschund": require(`./../images/profile-pictures/daschund.webp`).default,
    "hills": require(`./../images/profile-pictures/hills.webp`).default,
    "macaw": require(`./../images/profile-pictures/macaw.webp`).default,
    "mountains": require(`./../images/profile-pictures/mountains.webp`).default,
    "tulip": require(`./../images/profile-pictures/tulip.webp`).default,
};

export const getProfileImage = (filename) => {
    return profileImages[filename];
}


const serviceIcons = {
    "icon": require(`./../images/service-icons/icon.webp`).default,
};

export const getServiceIcon = (filename) => {
    return serviceIcons[filename];

}