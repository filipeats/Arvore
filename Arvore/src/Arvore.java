public class Arvore {
	// Propriedades da classe
	private No raiz = null;
	
	// Métodos da classe
	private int getAlturaDoNo(No raiz) {
		int retorno = 0;
		
		if (raiz != null) {
			retorno = raiz.getAltura();
		}
		
		return retorno;
	}
	
	private int getMaximoEntreDoisNumeros(int a, int b) {
		return (a > b) ? a : b;
	}
	
	private int getBalanco(No raiz) {
		int retorno = 0;
		
		if (raiz != null) {
			retorno = (getAlturaDoNo(raiz.getEsquerda()) -
					   getAlturaDoNo(raiz.getDireita()));
		}
		
		return retorno;
	}
	
	private No rotacaoEsquerda(No raiz) {
		No novaRaiz = raiz.getDireita();
		No temp = novaRaiz.getEsquerda();
		
		novaRaiz.setEsquerda(raiz);
		raiz.setDireita(temp);
		
		raiz.setAltura(getMaximoEntreDoisNumeros(
					   getAlturaDoNo(raiz.getEsquerda()),
					   getAlturaDoNo(raiz.getDireita())) + 1);
		novaRaiz.setAltura(getMaximoEntreDoisNumeros(
						   getAlturaDoNo(novaRaiz.getEsquerda()),
						   getAlturaDoNo(novaRaiz.getDireita())) + 1);
		
		return novaRaiz;
	}
	
	private No rotacaoDireita(No raiz) {
		No novaRaiz = raiz.getEsquerda();
		No temp = novaRaiz.getDireita();
		
		novaRaiz.setDireita(raiz);
		raiz.setEsquerda(temp);
		
		raiz.setAltura(getMaximoEntreDoisNumeros(
					   getAlturaDoNo(raiz.getEsquerda()),
					   getAlturaDoNo(raiz.getDireita())) + 1);
		novaRaiz.setAltura(getMaximoEntreDoisNumeros(
						   getAlturaDoNo(novaRaiz.getEsquerda()),
						   getAlturaDoNo(novaRaiz.getDireita())) + 1);
		
		return novaRaiz;
	}
	
	public void inserir(int numero) {
		raiz = inserir(raiz, numero);
	}
	
	private No inserir(No raiz, int numero) {
		// Inserção do número
		if (raiz == null) {
			raiz = new No(numero, 1, null, null);
		} else if (raiz.getNumero() > numero) {
			raiz.setEsquerda(inserir(raiz.getEsquerda(), numero));
		} else {
			raiz.setDireita(inserir(raiz.getDireita(), numero));
		}
		
		// Rebalanceamento da árvore
		raiz.setAltura(1 + getMaximoEntreDoisNumeros(
						   getAlturaDoNo(raiz.getEsquerda()),
						   getAlturaDoNo(raiz.getDireita())));
		
		int balanco = getBalanco(raiz);
		
		// Subárvore esquerda maior, inserir à esquerda
		if ((balanco > 1) &&
			(numero < raiz.getEsquerda().getNumero())) {
			raiz = rotacaoDireita(raiz);
		}
		
		// Subárvore direita maior, inserir à direita
		if ((balanco < -1) &&
			(numero > raiz.getDireita().getNumero())) {
			raiz = rotacaoEsquerda(raiz);
		}
		
		// Subárvore esquerda maior, inserir à direita
		if ((balanco > 1) &&
			(numero > raiz.getEsquerda().getNumero())) {
			raiz.setEsquerda(rotacaoEsquerda(raiz.getEsquerda()));
			raiz = rotacaoDireita(raiz);
		}
		
		// Subárvore direita maior, inserir à esquerda
		if ((balanco < -1) &&
			(numero < raiz.getDireita().getNumero())) {
			raiz.setDireita(rotacaoDireita(raiz.getDireita()));
			raiz = rotacaoEsquerda(raiz);
		}		
		
		return raiz;
	}
	
	public void imprimir() {
		imprimir(raiz, "");
	}
	
	private void imprimir(No raiz, String indentacao) {
		if (raiz != null) {
			System.out.println(indentacao + raiz.getNumero());
			imprimir(raiz.getEsquerda(), indentacao + "...");
			imprimir(raiz.getDireita(), indentacao + "...");
		}
	}
}