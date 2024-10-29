'use client'

import { JSX } from 'react/jsx-runtime'

import { Dispatch, SetStateAction} from 'react'

interface Pages {
    pages: number
    currentPage: number
    setCurrentPage: Dispatch<SetStateAction<number>>
}


export default function PaginationComponent({pages, currentPage, setCurrentPage}: Pages) {

    const field: JSX.Element[] = [];

    for (let i = 1; i <= pages; i++) {

        if(pages == 1) {
            return
        }

        if(i === 1) {
            field.push(<p
                onClick={() => currentPage > 1 ? setCurrentPage(currentPage - 1) : null} 
                className={`hover:cursor-pointer select-none`}>{"<"}</p>)
        }


        if(currentPage == i || currentPage - 1 == i || currentPage - 2 == i || currentPage + 1 == i || currentPage + 2 == i) {
            
            field.push(
                <p 
                    onClick={() => setCurrentPage(i)}
                    className={`hover:cursor-pointer select-none ${i == currentPage ? 'text-[#8BCB1A]' : null}`}>{i}</p>
            )

            if(currentPage - 3 > 0 && field[1].props.children !== "...") {
                field.splice(1, 0,
                    <p 
                        className={`hover:cursor-pointer select-none ${i == currentPage ? 'text-[#8BCB1A]' : null}`}>...</p>
                )
            }
        }


        if(i === pages && currentPage != pages) {
            field.push(<p
                onClick={() => i <= pages ? setCurrentPage(currentPage + 1) : null} 
                className={`hover:cursor-pointer select-none`}>{">"}</p>)
        }

        if(i === pages - 1 && currentPage <= pages - 3) {
            field.push(<p 
                className={`hover:cursor-pointer select-none`}>{"..."}</p>)
        }
    }
    

    return(
        <div className={`flex gap-2 text-md m-auto justify-center w-full`}>
            {field}
        </div>
    )
}