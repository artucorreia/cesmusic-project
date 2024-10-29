import thisComponentImage from '../assets/audio_software_without_top_grading.jpg'
import LandingPagePattern from './landingPagePattern'

export default function LandingPageC() {
    return(
        <LandingPagePattern
            background='bg-audio_software '
            image={thisComponentImage}
            title={<div className='flex flex-col'>
                <h1 className='mt-3'>S O F T W A R E</h1>
                <span className="-mt-2">D E</span>
                <span className='-mt-2'>Á U D I O</span>
            </div>}
            paragraph={'Softwares de áudio são programas desenvolvidos com o intuito de mixar, editar, alterar, criar, reproduzir e até mesmo melhorar os sons, tudo isso digitalmente. Esses softwares são importantíssimos e sua utilização abrange tanto o usuário comum, como grandes engenheiros de som, permitindo a manipulação de gravações de áudio para obter um melhor resultado final'}
            reverse={false}
            textAlignment={'text-left'}
        />
    )
}