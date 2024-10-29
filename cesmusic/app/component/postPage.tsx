'use client'

import { BsArrowLeftShort } from "react-icons/bs";

import style from '../styles/postPage.module.css'

import { useRouter } from "next/navigation";

export default function PostPage() {

    const router = useRouter()

    const onHandleReturnToLastPage = () => {

        const lastPage:string|null = sessionStorage.getItem('lastPage')

        console.log(lastPage)

        router.push(`/${lastPage}`)
    }

    return(
        <div className={`${style.container} `}>

            <BsArrowLeftShort 
                onClick={onHandleReturnToLastPage}
                className="text-[2.5rem] absolute top-10 hover:text-blue-300 hover:cursor-pointer"/>

            <h1>TÍTULO DA POSTAGEM</h1>

            <p className='w-[100%] mt-7'>{'Lorem ipsum dolor sit amet consectetur adipisicing elit. Dolorem nemo cupiditate illo, animi dolore saepe sequi doloremque veniam nostrum, debitis earum laboriosam dignissimos sapiente nulla. Odit provident quia tempora cupiditate.'}</p>

            <div className='w-[100%] bg-gray-500 h-[20rem] m-auto mt-7'/>

            <div>
                <p className='text-right opacity-65 text-sm'>Autor: (AUTOR)</p>
                <p className='text-right opacity-65 text-sm'>Data de postagem: (Data de postagem)</p>
            </div>

        </div>
    )
}