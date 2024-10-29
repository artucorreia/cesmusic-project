import { StaticImageData } from "next/image"
import { ReactNode } from "react"

export default interface LandingPageComponent {
    background: string
    image: StaticImageData
    title: string|ReactNode
    paragraph: string
    reverse: boolean,
    textAlignment: "text-left"|"text-right"
}