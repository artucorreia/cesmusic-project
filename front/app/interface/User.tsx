import Role from "../enum/Role";

export interface User {
    email: string,
    password: string,
    name: string,
    role: Role,
    about: string,
    createdAt: string,
    active: boolean
}