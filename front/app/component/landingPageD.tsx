import LandingPagePattern from './landingPagePattern'
import thisComponentImage from '../assets/eletronic_composition.jpg'

export default function LandingPageD() {
    return(
        <LandingPagePattern
            background='bg-eletronic_composition'
            image={thisComponentImage}
            title={<div>
                <h1>T E C N O L O G I A</h1>
                <span className='-mt-2'>M U S I C A L</span>
            </div>}
            paragraph={`A tecnologia na música diz respeito a utilização de dispositivos eletrônicos que compõem o trabalho do artista para chegar no resultado final de sua obra. Um bom exemplo de tecnologia musical que pode passar despercebida de ser tida como “tecnologia musical”, são os pedais para guitarra, que distorcem e alteram o som do instrumento`}
            reverse={true}
            textAlignment={'text-right'}
        />
    )
}