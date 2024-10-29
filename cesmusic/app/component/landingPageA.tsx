'use client'

import style from '../styles/landingPageA.module.css'
import Image from 'next/image'

import logo_text from '../assets/logo_text.svg'
import scrollDown from '../assets/scrollDown.svg'

import { useRouter } from 'next/navigation'

import WhichPage from '../enum/WhichPage'

export default function LandingPageA() {

    const router = useRouter()
    
    const onHandleRouteToBlog = (where: WhichPage) => {
        if(where == WhichPage.eletronic_composion) {
            router.push('/eletronic_composition?page=1')
        } else if(where == WhichPage.musical_tec) {
            router.push('/musical_tec?page=1')
        } else {
            router.push('/audio_software?page=1')
        }
    }

    const registerPageHandler = () => {
        router.push('/register')
    }

    return(
        <div className={`${style.container}`}>

            <Image
                src={logo_text}                
                alt={"cesmusic-logo"}
                className={`${style.cesmusic_title}`}
            />

            <Image
                src={scrollDown}
                alt={"scroll_down"}
                className={`${style.scroll_down} animate-pulse`}
            />

            <div className='text-white flex gap-10 mt-8 hover:cursor-pointer '>
                <h1 
                    onClick={() => onHandleRouteToBlog(WhichPage.eletronic_composion)}
                    className='hover:underline-offset-4 hover:underline'>Composição Eletrônica
                </h1>

                <h1 
                    onClick={() => onHandleRouteToBlog(WhichPage.musical_tec)}
                    className='hover:underline-offset-4 hover:underline'>Tecnologia Musical
                </h1>

                <h1
                    onClick={() => onHandleRouteToBlog(WhichPage.audio_software)} 
                    className='hover:underline-offset-4 hover:underline'>Software de Áudio</h1>
            </div>

            <p 
                onClick={() => registerPageHandler()}
                className='text-white text-center mt-6 absolute right-0 top-0 mr-10 underline hover:cursor-pointer'>Crie sua conta
            </p>

        </div>
    )
}