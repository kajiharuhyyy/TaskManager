package com.example.demo.web;

import jakarta.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.demo.domain.Member;
import com.example.demo.repo.MemberRepository;
import com.example.demo.web.form.MemberForm;

import lombok.RequiredArgsConstructor;

@Controller //HTTPリクエストを受け取って、画面(HTML)を返すクラス
@RequiredArgsConstructor //finalなフィールドを引数に持つコンストラクタを自動生成
public class MemberController {
	
	private final MemberRepository memberRepository; // finalにすることで、コンストラクタで初期化される
	
	@GetMapping("/members")
	public String listMembers(Model model) {
		model.addAttribute("members", memberRepository.findAll()); // メンバー一覧をモデルに追加
		return "members/list";
	} // メンバー一覧画面を表示するためのメソッド
	
	@GetMapping("/members/new")
	public String newMemberForm(Model model) {
		MemberForm form = new MemberForm(); // 空のフォームオブジェクトを作成
		form.setActive(true); // デフォルトで有効に設定
		model.addAttribute("memberForm", form); // 新規作成画面に空のフォームオブジェクトを追加
		return "members/new";
	} // 新規作成画面を表示するためのメソッド
	
	@PostMapping("/members")
	public String createMember(@Valid @ModelAttribute MemberForm memberForm,
			BindingResult bindingResult) {
		
		if (bindingResult.hasErrors()) {
			return "members/new"; // バリデーションエラーがある場合、新規作成画面を再表示
		}
		
		Member member = Member.builder()
				.name(memberForm.getName())
				.email(memberForm.getEmail())
				.active(memberForm.getActive())
				.build(); // フォームの入力値を使って新しいメンバーオブジェクトを作成
		
		memberRepository.save(member); // メンバーを保存
		
		return "redirect:/members"; // メンバー一覧画面にリダイレクト
	} // 新規メンバーを作成するためのメソッド
	
	@GetMapping("/members/{id}/edit")
	public String editMemberForm(@PathVariable Long id, Model model) {
		Member member = memberRepository.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("Invalid member id " + id)); // メンバーが存在しない場合の例外処理
		
		MemberForm form = new MemberForm(); // 既存のメンバー情報をフォームオブジェクトにセット
		form.setId(member.getId()); // IDもセット
		form.setName(member.getName()); // 名前をセット
		form.setEmail(member.getEmail());  // メールをセット
		form.setActive(member.isActive());  // 有効/無効をセット
		
		model.addAttribute("memberForm", form); // 編集画面に既存のメンバー情報を追加
		return "members/edit";
	} // メンバー編集画面を表示するためのメソッド
	
	@PostMapping("/members/{id}/edit")
	public String updateMember(@PathVariable Long id,
			@Valid @ModelAttribute MemberForm memberForm,
			BindingResult bindingResult) {
		
		if (bindingResult.hasErrors()) {
			return "members/edit"; // バリデーションエラーがある場合、編集画面を再表示
		}
		
		Member member = memberRepository.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("Invalid member id " + id)); // メンバーが存在しない場合の例外処理
	
		member.setName(memberForm.getName()); // フォームの入力値で名前を更新
		member.setEmail(memberForm.getEmail()); // フォームの入力値でメールを更新
		member.setActive(memberForm.getActive()); // フォームの入力値で有効/無効を更新
		
		memberRepository.save(member); // メンバーを保存
		
		return "redirect:/members"; // メンバー一覧画面にリダイレクト
		
		} // 既存メンバーを更新するためのメソッド
	
	@PostMapping("/members/{id}/delete")
	public String delateMember(@PathVariable Long id) {
		memberRepository.deleteById(id); // メンバーを削除
		return "redirect:/members";
	} 	// メンバーを削除するためのメソッド
}
