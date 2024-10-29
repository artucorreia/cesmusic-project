'use client'

import { Suspense } from 'react'


import PagePattern from '../component/pagePattern'
import WhichPage from '../enum/WhichPage'

export default function EletronicComposion() {

    return(
        <div>
            <Suspense fallback={<div>Loading...</div>}>
                <PagePattern
                welcomeText={"🖐️ Oi!"}
                linearGradient='linear-gradient(45deg, #E0EFED, #83D79E, #EC9435)'
                detailsColorA='#EC9435'
                detailsColorB='#69D1C5'
                whichPage={WhichPage.eletronic_composion}
                />
            </Suspense>
        </div>
    )
}