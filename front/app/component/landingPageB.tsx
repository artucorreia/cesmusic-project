import LandingPagePattern from '../component/landingPagePattern'
import thisComponentImage from '../assets/musical_tecnology.jpg'

export default function LandingPageB() {
    return(
        <LandingPagePattern
            background='bg-musical_tecnology'
            image={thisComponentImage}
            title={<div>
                <h1>C O M P O S I Ç Ã O</h1>
                <span className='-mt-2'>E L E T R Ô N I C A</span>
            </div>}
            paragraph={'A composição eletrônica, que dá luz às músicas eletrônicas, são composições que fogem do padrão de criação pelo fato da manipulação e edição dos sons serem bem mais visíveis e utilizadas em escala muito maior. A composição eletrônica se diferencia também por conta dos equipamentos eletrônicos utilizados no seu processo de criação'}
            reverse={false}
            textAlignment={'text-left'}
        />
    )
}