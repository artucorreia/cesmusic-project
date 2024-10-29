'use client'

import Link from 'next/link'


import { CiPlay1 } from "react-icons/ci";

import { useEffect, useState } from 'react';

import styles from '../styles/pagePattern.module.css'

import PaginationComponent from './paginationComponent';

import { useRouter, useSearchParams } from 'next/navigation';
import WhichPage from '../enum/WhichPage';

interface PatternProps {
    whichPage: WhichPage,
    welcomeText: string,
    linearGradient: any,
    detailsColorA: any,
    detailsColorB: any
}


export default function PagePattern({whichPage, welcomeText, linearGradient, detailsColorA, detailsColorB}: PatternProps) {

    const router = useRouter()
    const params = useSearchParams()

    const actualPage:string|null = params.get('page')

    const [searching, setSearching] = useState(false)
    const [currentPage, setCurrentPage] = useState<number>(Number(actualPage))

    useEffect(() => {

        if(whichPage == WhichPage.audio_software) {
            router.push(`/audio_software?page=${currentPage.toString()}`)
        } else if(whichPage == WhichPage.musical_tec) {
            router.push(`/musical_tec?page=${currentPage.toString()}`)
        } else if(whichPage == WhichPage.eletronic_composion) {
            router.push(`/eletronic_composition?page=${currentPage.toString()}`)
        }
    }, [currentPage])

    const onHandleSearching = (element: any) => {

        if(element.target.value.trim() != "" && typeof element.target.value.trim() == "string") {
            setSearching(true)
        } else{
            setSearching(false)
        }
    }  

    const postList = [
        {
            title: 'TítuloA',
            textPreview: `Lorem ipsum dolor sit amet consectetur adipisicing elit. 
            Quasi labore quae, minima necessitatibus facere quaerat eum itaque 
            voluptates qui laudantium dolore reprehenderit voluptate libero 
            omnis ad quod nulla! Culpa, sit.`,
            imagePreview: 'img',
            postOwner: 'nome sobrenome',
            createdAt: '28/07/2024',
            id: 1
        },
        {
            title: 'TítuloB',
            textPreview: `Lorem ipsum dolor sit amet consectetur adipisicing elit. 
            Quasi labore quae, minima necessitatibus facere quaerat eum itaque 
            `,
            imagePreview: 'img',
            postOwner: 'teste dois da silva',
            createdAt: '28/07/2024',
            id: 2
        },
        {
            title: 'TítuloC',
            textPreview: `Lorem ipsum dolor sit amet consectetur adipisicing elit. 
            Quasi labore quae, minima necessitatibus facere quaerat eum itaque 
            dasd asd fasdf asd ei jdasw `,
            imagePreview: 'img',
            postOwner: 'teste três da silva',
            createdAt: '28/07/2024',
            id: 3
        }
    ]

    const onHandlerGoToPost = () => {

        let page: string = ""

        if(whichPage == WhichPage.audio_software) {
            page = "audio_software"
        } else if(whichPage == WhichPage.musical_tec) {
            page = "musical_tec"
        } else if(whichPage == WhichPage.eletronic_composion) {
            page = "eletronic_composition"
        }

        sessionStorage.setItem('lastPage', `${page}?page=${currentPage.toString()}`)
        router.push(`/${page}/12345`)
    }

    const pageStyle: any = {
        "--logo-gradient": linearGradient,
        "--details-color-a": detailsColorA,
        "--details-color-b": detailsColorB
    }  

    return(
            <div 
                style={pageStyle}
                className={styles.container}>
                <div className={styles.header}>
                    <Link href="/">
                        <h1 id={styles.logo_text} className='font-semibold'>CesMusic</h1>
                    </Link>

                    <h2>{welcomeText}</h2>

                    <div className={styles.input_block}>
                        <input 
                            onChange={(element) => onHandleSearching(element)}
                            type="text" 
                            name="search" 
                            id={styles.search} 
                            placeholder="Pesquise alguma postagem aqui" 
                        />
                        
                        <button>
                            <CiPlay1 id={styles.play} className={`text-xl transition-all duration-500 ${searching ? styles.show : styles.not_show}`}/>
                            <div id={styles.circle} className={`${!searching ? styles.show : styles.not_show}`}></div>    
                        </button>

                    </div>

                </div>

                {postList.map((element, index) => (
                    <div className={`${styles.post} ${index % 2 == 0 ? styles.postA : styles.postB}`} key={element.id}>

                        <div key={element.id} className={styles.title_and_text}>
                            <h1 className='font-semibold'>{element.title}</h1>
                            <p>{element.textPreview}</p>
                        </div>

                        <div  className={styles.info_bellow}>
                            <button onClick={onHandlerGoToPost}>
                                <p>Acessar Publicação</p>
                            </button>

                            <div className={`${styles.likes_and_owner}`}>

                                <p id={styles.owner_name}>Post feito por {element.postOwner}</p>
                            </div>
                        </div>
                        
                    </div>
                ))}

                    <PaginationComponent
                        currentPage={currentPage}
                        pages={10}
                        setCurrentPage={setCurrentPage}
                    />

            </div>
    )
}