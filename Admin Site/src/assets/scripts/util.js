export const toKebabCase = (str) => {
    return str.split('').map((letter, idx) => {
        return letter.toUpperCase() === letter
            ? `${idx !== 0 ? '-' : ''}${letter.toLowerCase()}`
            : letter;
    }).join('');
}

export const getProfileImage = (filename) => {
    return require(`./../../images/profile-pictures/${filename}.webp`).default
}