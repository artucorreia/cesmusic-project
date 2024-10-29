import Image from 'next/image'

import style from '../styles/landingPagePattern.module.css'

import { lindenHill } from '../font'
import LandingPageComponent from '../interface/LandingPageComponent'

export default function LandingPagePattern({background, image, title, paragraph, reverse, textAlignment}: LandingPageComponent) {
    return(
        <div className={`${style.container} ${background}`}>

            <div className={`${style.wrapper} ${reverse ? 'flex-row-reverse' : 'flex-row'}`}>
            
                <div className={`${style.section_image}`}>
                    <Image
                      src={image}
                      alt='guy_with_a_guitar'
                    />
                </div>

                <div className={`${style.text_area}`}>
                    <div className={`${style.section_title}`}>
                        {title}
                    </div>

                    <p className={`${lindenHill.className} ${textAlignment}`}>{paragraph}</p>
                </div>

            </div>

        </div>
    )
}